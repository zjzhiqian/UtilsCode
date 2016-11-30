package com.hzq.project.test.service.impl;

import com.hzq.project.test.entity.Account;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by hzq on 16/11/28.
 */
@Service("accountService")
public class AccountService  implements InitializingBean{
    private MyCacheManager<Account> cacheManager;

    public AccountService() {
        cacheManager = new MyCacheManager<Account>();// 构造一个缓存管理器
    }


    @Cacheable(cacheNames = "accountCache")
    public Account getAccountByName(String acctName) {
        return getFromDB(acctName);// 否则到数据库中查询
    }

    @CacheEvict(cacheNames = "accountCache", key = "#account.getName()")
//    @CachePut(value="default",key="#account.getName()")// 更新 accountCache 缓存
    public void updateAccount(Account account) {
        updateDb(account);

    }


    private Account updateDb(Account account) {
        System.out.println("update db.....");
        return account;
    }

    private Account getFromDB(String acctName) {
        System.out.println("real querying db..." + acctName);
        return new Account(acctName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(1);
    }
}
