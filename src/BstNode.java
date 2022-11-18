public class BstNode<T extends Comparable<T>> {

    private BstNode<T> Parent;
    private BstNode<T> LeftChild ;
    private BstNode<T> RightChild;
    private T data;
    private int level;
    private int sumLeftSide = 0;
    private int sumRightSide = 0;
    public boolean visited = false;
    public boolean cleanUpVisited = false;

    public BstNode(BstNode pParrent, T pdata, int pLevel) {
        Parent = pParrent;
        data = pdata;
        RightChild = null;
        LeftChild = null;
        level = pLevel;
    }
    public void setSumLeftSide(int pSumLeftSide) {
        sumLeftSide = pSumLeftSide;
    }
    public void setSumRightSide(int pSumRightSide) {
        sumRightSide = pSumRightSide;
    }
    public int getSumLeftSide() {
        return sumLeftSide;
    }
    public int getSumRightSide() {
        return sumRightSide;
    }
    public void incrementLeftSide() {
        sumLeftSide++;
    }
    public void decrementLeftSide() {
        sumLeftSide--;
    }
    public void incrementRightSide() {
        sumRightSide++;
    }
    public void decrementRightSide() {
        sumRightSide--;
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public BstNode getParent() {
        return Parent;
    }

    public BstNode getLeftChild() {
        return LeftChild;
    }

    public BstNode getRightChild() {
        return RightChild;
    }

    public T getData() {
        return data;
    }
    public void setParent(BstNode parent) {
        Parent = parent;
    }

    public void setLeftChild(BstNode leftChild) {
        LeftChild = leftChild;
    }

    public void setRightChild(BstNode rightChild) {
        RightChild = rightChild;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isItLeftChild(BstNode<T> pChild) {
        return LeftChild == pChild;
    }
    public boolean isItRightChild(BstNode<T> pChild) {
        return RightChild == pChild;
    }
    public void  replaceMeWith(BstNode<T> pNode) {
        if (Parent != null) {
            boolean isItLeftChild = this.Parent.isItLeftChild(this);
            if (isItLeftChild )
                this.Parent.setLeftChild(pNode);
            else
                this.Parent.setRightChild(pNode);
        }
        //new
        pNode.setLevel(this.level);
        if (this.LeftChild != null) {
            if (LeftChild.getData().compareTo(pNode.getData()) != 0) {
                this.LeftChild.setParent(pNode);
                pNode.setLeftChild(this.LeftChild);
            }
        }
        if (this.RightChild != null) {
            if (RightChild.getData().compareTo(pNode.getData()) != 0) {
                this.RightChild.setParent(pNode);
                pNode.setRightChild(this.RightChild);
            }
        }
        pNode.getParent().deleteChild(pNode.getData());
        pNode.setParent(Parent);

    }
    public boolean deleteChild(T pData) {
        if (LeftChild != null){
            if (pData.compareTo( LeftChild.getData()) == 0) {
                LeftChild.setParent(null);
                LeftChild = null;
                return true;
            }
        }
        if (RightChild != null) {
            if (pData.compareTo( RightChild.getData()) == 0) {
                RightChild.setParent(null);
                RightChild = null;
                return true;
            }
        }
        return false;
    }
    public boolean replaceChild(BstNode<T> pChild, BstNode<T> newChild) {
        if (LeftChild != null){
            if (pChild.getData().compareTo(LeftChild.getData()) == 0) {
                LeftChild = newChild;
                if (newChild != null) {
                    newChild.setParent(this);
                    newChild.setLevel(pChild.level); //new
                }
                return true;
            }
        }
        if (RightChild != null) {
            if (pChild.getData().compareTo(RightChild.getData()) == 0) {
                RightChild = newChild;
                if (newChild != null) {
                    newChild.setParent(this);
                    newChild.setLevel(pChild.level); //new
                }
                return true;
            }
        }
        return false;
    }
    public int getNumOfSuns() {
        int num = 0;
        if (LeftChild != null)
            num++;
        if (RightChild != null)
            num++;
        return num;
    }
}
