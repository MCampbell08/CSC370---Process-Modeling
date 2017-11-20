public class CompressonDriver {
    public static void main(String[] args){
        byte[] rawData = {
                2,2,2,2,2,3,3,3,3,3,2,2,2,2,2,2,7,7,7,7,7,7,7,7,7,1,1,1,1,1,1,1,1,0
        };
        HuffCompressor c = new HuffCompressor();
        c.compress(rawData);
    }
}
