package main.generator;

import java.util.List;

/**
 * Base Generator class with builder
 */
public class BaseGenerator implements IGenerator {

    private int mStart;
    private int mStop;
    private int mIncrement;
    private String mPrefix;
    private String mSuffix;
    private int mOrder;
    private boolean mPadding;

    private BaseGenerator(int mStart, int mStop, int mIncrement, String mPrefix, String mSuffix, int mOrder, boolean padding) {
        this.mStart = mStart;
        this.mStop = mStop;
        this.mIncrement = mIncrement;
        this.mPrefix = mPrefix;
        this.mSuffix = mSuffix;
        this.mOrder = mOrder;
        this.mPadding = padding;
    }


    @Override
    public List<String> generateNumbers() {

        switch(mOrder){

            case 0 :
            case 1 : // Normal Order
                return utils.generateListNormalOrder(mStart, mStop, mPrefix, mSuffix, mIncrement, mPadding);
            case 2 : // Reverse Order
                return utils.generateListReverseOrder(mStart, mStop, mPrefix, mSuffix, mIncrement, mPadding);
            case 3 : // Random Order
                return utils.generateListRandomOrder(mStart, mStop, mPrefix, mSuffix, mIncrement, mPadding);
            default: break;
        }
        return null;
    }


    public static class Builder {

        private int mStart;
        private int mStop;
        private int mIncrement;
        private String mPrefix;
        private String mSuffix;
        private int mOrder;
        private boolean mPadding;


        public Builder(int start, int stop, int increment, boolean padding){

            this.mStart = start;
            this.mStop = stop;
            this.mIncrement = increment;
            this.mPadding = padding;
        }


        public Builder prefix(String prefix) {
            this.mPrefix = prefix;
            return this;
        }

        public Builder suffix(String suffix) {
            this.mSuffix = suffix;
            return this;
        }

        public Builder order(int order){
            this.mOrder = order;
            return this;
        }

        public  BaseGenerator build(){
            return new BaseGenerator(mStart, mStop, mIncrement, mPrefix, mSuffix, mOrder, mPadding);
        }
    }
}
