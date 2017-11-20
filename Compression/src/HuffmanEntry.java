public class HuffmanEntry {

    private Byte value;

    private int frequency;

    public HuffmanEntry _left, _right;

    public HuffmanEntry(){}

    public HuffmanEntry(HuffmanEntry left, HuffmanEntry right) {
        this(null, left.getFrequency() + right.getFrequency());
        this._left = left;
        this._right = right;
    }

    public HuffmanEntry(Byte value, Integer v) {
        this.setValue(value);
        this.setFrequency(v);
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public HuffmanEntry incrementFrequency(){
        this.frequency++;
        return this;
    }

    @Override
    public String toString(){
        return "V=" + this.getValue() + "-F=" + this.getFrequency() + " left=" + _left + " right=" + _right;
    }
}