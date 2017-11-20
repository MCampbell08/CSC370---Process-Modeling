import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HuffCompressor {

    private List<HuffmanEntry> list = new ArrayList<>();

    private HuffmanEntry root = null;

    private void convertTableToTree(){
        while(list.size() > 1){
            HuffmanEntry left = list.remove(0);
            HuffmanEntry right = list.remove(0);

            HuffmanEntry parent = new HuffmanEntry(left, right);
            list.add(parent);
            list.sort((l, r) -> Integer.compare(l.getFrequency(), r.getFrequency()));
        }
        root = list.get(0);
    }

    public void compress(byte[] data){
        buildTable(data);
        convertTableToTree();
        System.out.print(list);
    }

    private void buildTable(byte[] data) {
        Map<Byte, HuffmanEntry> map = new HashMap<>();
        for(int i = 0; i<data.length; i++){
            byte cur = data[i];
            map.compute(cur, (k, v) -> map.get(k) == null ? new HuffmanEntry(k, 1) : map.get(k).incrementFrequency());
        }
        list = map.values().stream().sorted((l, r) -> Integer.compare(l.getFrequency(), r.getFrequency())).collect(Collectors.toList());
    }
}
