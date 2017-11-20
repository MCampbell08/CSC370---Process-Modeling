import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffCompressor {

    private List<HuffmanEntry> list = new ArrayList<>();

    private HuffmanEntry root = null;

    public void compress(byte[] data){
        buildTable(data);
    }
    private void buildTable(byte[] data) {
        Map<Byte, Integer> map = new HashMap<>();
        for(int i = 0; i<data.length; i++){
            byte cur = data[i];

        }
    }
}
