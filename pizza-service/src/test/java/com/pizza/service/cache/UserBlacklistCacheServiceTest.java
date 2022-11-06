package com.pizza.service.cache;

import com.pizza.configuration.cache.CacheConfiguration;
import com.spring5microservices.common.service.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserBlacklistCacheServiceTest {

    @Mock
    private CacheConfiguration mockCacheConfiguration;

    @Mock
    private CacheService mockCacheService;

    private UserBlacklistCacheService service;

    @BeforeEach
    public void init() {
        service = new UserBlacklistCacheService(mockCacheConfiguration, mockCacheService);
        when(mockCacheConfiguration.getUserBlacklistCacheName())
                .thenReturn("TestCache");
    }


    private List<Object[]> containsTestCases() {
        String username = "username";
        return asList(
                //             username,   cacheServiceResult,   expectedResult
                new Object[] { null,       false,                false },
                new Object[] { username,   false,                false },
                new Object[] { username,   true,                 true });
    }

    @Test
    public void contains_testCases() {
        for (Object[] parameters: containsTestCases()) {
            when(mockCacheService.contains(anyString(), eq((String)parameters[0]))).thenReturn((boolean)parameters[1]);
            boolean operationResult = service.contains((String)parameters[0]);
            assertEquals(parameters[2], operationResult);
        }
    }


    private List<Object[]> putTestCases() {
        String username = "username";
        return asList(
                //             username,   cacheServiceResult,   expectedResult
                new Object[] { null,       false,                false },
                new Object[] { username,   false,                false },
                new Object[] { username,   true,                 true });
    }

    @Test
    public void put_testCases() {
        for (Object[] parameters: putTestCases()) {
            when(mockCacheService.put(anyString(), eq((String)parameters[0]), anyBoolean())).thenReturn((boolean)parameters[1]);
            boolean operationResult = service.put((String)parameters[0]);
            assertEquals(parameters[2], operationResult);
        }
    }


    private List<Object[]> removeTestCases() {
        String username = "username";
        return asList(
                //             username,   cacheServiceResult,   expectedResult
                new Object[] { null,       false,                false },
                new Object[] { username,   false,                false },
                new Object[] { username,   true,                 true });
    }

    @Test
    public void remove_testCases() {
        for (Object[] parameters: removeTestCases()) {
            when(mockCacheService.remove(anyString(), eq((String)parameters[0]))).thenReturn((boolean)parameters[1]);
            boolean operationResult = service.remove((String)parameters[0]);
            assertEquals(parameters[2], operationResult);
        }
    }

}
