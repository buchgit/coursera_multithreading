package com.example.coursera_multithreading.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MockGenerator {

    public static List<Mock> generate(int count){

        Random random = new Random();

        List<Mock> mocks = new ArrayList<>(count);

        for (int i=0;i<count;i++){
            mocks.add(new Mock(UUID.randomUUID().toString(),random.nextInt(200)));
        }

        return mocks;
    }

}
