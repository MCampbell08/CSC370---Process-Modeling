public class HuffmanEntry {

    private byte value;

    private int frequency;

    public HuffmanEntry left, child;



    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
