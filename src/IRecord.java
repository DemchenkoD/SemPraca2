public interface IRecord<T> {
    public byte[] ToByteArray();
    public void FromByteArray (byte[] paArray);
    public int getSize ();
}

