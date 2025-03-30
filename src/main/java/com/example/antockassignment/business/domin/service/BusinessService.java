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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@Slf4j
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
                } catch (Exception exception) {
                    log.error("Failed to retrieve public data for 사업자번호: {}. Error: {}", data.brno(), exception.getMessage(), exception);
                }

                PublicAddress publicAddress = null;

                String addressToUse = data.lctnAddr().replaceAll(" ", "").isEmpty() ? publicData.items().get(0).lctnRnAddr() : data.lctnAddr();

                if (data.isLctnAddrNumeric()) {
                    addressToUse = publicData.items().get(0).lctnRnAddr();
                }

                if (!"N/A".equals(addressToUse)) {
                    try {
                        publicAddress = businessJusoApi.getBusinessJusoData(addressToUse);
                        if (publicAddress != null && publicAddress.results().common().totalCount() > 0) {
                            admCd = publicAddress.results().juso().get(0).admCd();
                        }
                    } catch (Exception exception) {
                        log.error("Failed to retrieve Juso data for 주소: {}. Error: {}", addressToUse, exception.getMessage(), exception);
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
