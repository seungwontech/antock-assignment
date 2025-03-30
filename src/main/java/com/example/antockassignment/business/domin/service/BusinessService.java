package com.example.antockassignment.business.domin.service;

import com.example.antockassignment.api.BusinessJusoApi;
import com.example.antockassignment.api.PublicDataApi;
import com.example.antockassignment.business.domin.repository.BusinessRepository;
import com.example.antockassignment.config.exception.CoreException;
import com.example.antockassignment.config.exception.ErrorType;
import com.example.antockassignment.selenium.dto.CSVData;
import com.example.antockassignment.api.dto.PublicData;
import com.example.antockassignment.api.dto.pubilcAddress.PublicAddress;
import com.example.antockassignment.business.domin.entity.Business;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@RequiredArgsConstructor
@Service
public class BusinessService {

    private final PublicDataApi publicDataApi;
    private final BusinessJusoApi businessJusoApi;
    private final BusinessRepository businessRepository;

    @Transactional
    public void create(List<CSVData> csvDatas) throws Exception {
        Queue<Business> businesses = new ConcurrentLinkedQueue<>();
        ForkJoinPool threadPool = new ForkJoinPool(4);

        threadPool.submit(() -> {
            csvDatas.parallelStream().forEach(data -> {
                String crno = "";
                String admCd = "";

                PublicData publicData = null;
                try {
                    publicData = publicDataApi.getPublicData(data.brno());
                    if (publicData != null && publicData.totalCount() > 0) {
                        crno = publicData.items().get(0).crno();
                    }
                } catch (Exception e) {
                    throw new CoreException(ErrorType.PUBLIC_DATA_NOT_FOUND, e.getLocalizedMessage());
                }

                PublicAddress publicAddress = null;

                String addressToUse = "".equals(data.lctnAddr().replaceAll(" ", "")) ? publicData.items().get(0).lctnRnAddr() : data.lctnAddr();
                if (data.isLctnAddrNumeric()) {
                    addressToUse = publicData.items().get(0).lctnRnAddr();
                }

                if (!"N/A".equals(addressToUse)) {
                    try {
                        publicAddress = businessJusoApi.getBusinessJusoData(addressToUse);
                    } catch (Exception e) {
                        throw new CoreException(ErrorType.JUSO_DATA_NOT_FOUND, e.getLocalizedMessage());
                    }
                }

                if (publicAddress != null && publicAddress.results() != null) {
                    if (publicAddress.results().common().totalCount() > 0) {
                        admCd = publicAddress.results().juso().get(0).admCd();
                    }
                }
                Business businessInfo = Business.create(data.prmmiMnno(), data.bzmnNm(), data.brno(), crno, admCd);
                businesses.offer(businessInfo);
            });
        }).get();
        threadPool.shutdown();
        batchSaveBusinesses(businesses);
    }

    private void batchSaveBusinesses(Queue<Business> businesses) {
        int batchSize = 100;
        List<Business> batch = new ArrayList<>(batchSize);

        while (!businesses.isEmpty()) {
            batch.add(businesses.poll());
            if (batch.size() == batchSize || businesses.isEmpty()) {
                businessRepository.saveAll(batch);
                batch.clear();
            }
        }
    }
}
