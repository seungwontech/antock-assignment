package com.example.antockassignment.business.service;

import com.example.antockassignment.api.BusinessJusoApi;
import com.example.antockassignment.api.PublicDataApi;
import com.example.antockassignment.selenium.dto.CSVData;
import com.example.antockassignment.api.dto.PublicData;
import com.example.antockassignment.api.dto.pubilcAddress.PublicAddress;
import com.example.antockassignment.business.entity.Business;
import com.example.antockassignment.business.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BusinessService {

    private final PublicDataApi publicDataApi;
    private final BusinessJusoApi businessJusoApi;
    private final BusinessRepository businessRepository;

    @Transactional
    public void create(List<CSVData> csvDatas) throws Exception {
        List<Business> businesses = new ArrayList<>();
        for (CSVData data : csvDatas) {

            String crno = "";
            String admCd = "";

            PublicData publicData = publicDataApi.getPublicData(data.brno());
            if (publicData.totalCount() > 0) {
                crno = publicData.items().get(0).crno();
            }

            PublicAddress publicAddress = null;

            String addressToUse = "".equals(data.lctnAddr().replaceAll(" ", "")) ? publicData.items().get(0).lctnRnAddr() : data.lctnAddr();

            if (data.isLctnAddrNumeric()) {
                addressToUse = publicData.items().get(0).lctnRnAddr();
            }

            if (!"N/A".equals(addressToUse)) {
                publicAddress = businessJusoApi.getBusinessJusoData(addressToUse);
            }

            if (publicAddress != null && publicAddress.results().common().totalCount() > 0) {
                admCd = publicAddress.results().juso().get(0).admCd();
            }

            Business businessInfo = Business.create(data.prmmiMnno(), data.bzmnNm(), data.brno(), crno, admCd);
            businesses.add(businessInfo);
        }
        businessRepository.saveAll(businesses);
    }
}
