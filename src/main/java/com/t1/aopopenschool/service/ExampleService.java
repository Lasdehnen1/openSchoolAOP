package com.t1.aopopenschool.service;

import com.t1.aopopenschool.annotation.TrackAsyncTime;
import com.t1.aopopenschool.annotation.TrackTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ExampleService {

    private List<Integer> computePrimes(int limit) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i < limit; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                primes.add(i);
            }
        }
        return primes;
    }

    @TrackTime
    public void computePrimesSync() {
        List<Integer> primes = computePrimes(10000000);

    }
    @TrackAsyncTime
    public CompletableFuture<List<Integer>> computePrimesAsync() {
        List<Integer> primes = computePrimes(10000000);
        return CompletableFuture.completedFuture(primes);
    }

    @TrackTime
    public void processLargeArraySync() {
        int[] array = new int[10000000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i * 2;
        }
    }

    @TrackAsyncTime
    public CompletableFuture<Void> processLargeArrayAsync() {
        int[] array = new int[10000000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i * 2;
        }
        return CompletableFuture.completedFuture(null);
    }

}
