package com.liu.consumer;

import lombok.Data;

public class Test {
    /**
     * 将一个字典转成json字符串，value只有有字符串，嵌套字典（同样字典的value只有字符串和字典）
     *
     * {"a":"b",  "e":{"c":"d"}}
     * {"a":"b","c":"d"}
     * @param args
     */
    public static void main(String[] args) {
        Dictory dictory1 = new Dictory("a","b");

        Dictory dictory2 = new Dictory("a",new Dictory("b",new Dictory("c","d")));

        Dictory tempDictory1 = new Dictory("a","b");
        Dictory tempDictory2 = new Dictory("a","b");
            

        String result1 = transfer(new Dictory[]{dictory1});
        String result2 = transfer(new Dictory[]{dictory2});
        String result3 = transfer(new Dictory[]{tempDictory1,tempDictory2});
        System.out.println("result1:"+result1);
        System.out.println("result2:"+result2);
    }
    public static  String transfer(Dictory[] dictorys){
        StringBuilder result = new StringBuilder();
        result.append("{");
        doTransfer(dictorys,result);
        result.deleteCharAt(result.length() -1);
        result.append("}");
        return result.toString();
    }
    public static void doTransfer(Dictory[] dictorys,StringBuilder result){
        for(Dictory dictory: dictorys){
            result.append(dictory.getKey()).append(":");
            if(dictory.value instanceof Dictory){
                result.append("{");
                Dictory value = (Dictory) dictory.getValue();
                doTransfer(new Dictory[]{value} ,result);
                result.append("}");
            }else{
                result.append(dictory.getValue());
            }
            result.append(",");
        }


    }

    static class Dictory{
        String key;
        Object value;
        public Dictory(String key,Object value){
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
