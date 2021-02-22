package clientserver.PartOne;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class MyUUID implements Comparable<MyUUID> {
    private final String key;
    private final UUID uuid;

    public MyUUID(String key) {
        this.key = key;
        this.uuid = Encoder(key);
    }

    public static UUID Encoder(@NotNull final String key){

        return UUID.nameUUIDFromBytes(key.getBytes());

    }
    public static String Decoder(@NotNull final MyUUID myUUID){
        return myUUID.getStringUUID();
    }

    public String getStringUUID(){
        return key;
    }

    @Override
    public String toString(){
        return "key " + this.key;
    }
    public static void main(String[] args) {
        MyUUID test = new MyUUID("matan");
        System.out.println(test);
        System.out.println(test.Decoder(test));

    }

    @Override
    public int compareTo(@NotNull MyUUID o) {
        if(this.uuid.compareTo((o.uuid)) == -1)
        {
            System.out.println("Our object is smaller than other object");
            return -1;
        }
        if(this.uuid.compareTo((o.uuid)) == 0)
        {
            System.out.println("Our object is equals to other object");
            return 0;
        }
        if(this.uuid.compareTo((o.uuid)) == 1)
        {
            System.out.println("Our object is greater than other object");
            return 1;
        }
        return 0;
    }
}


