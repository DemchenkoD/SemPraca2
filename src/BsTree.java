import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

public class BsTree<T extends Comparable<T>> {
    private BstNode<T> Root;
    public int size = 0;

    public BsTree() {

    }
    public void cleanup() {
        Root = null;
        size = 0;
    }

    public boolean insertWithRotations(T pData) {
        if (Root == null) {
            Root = new BstNode<T>(null, pData, 1);
        } else {
            BstNode<T> node = Root;
            boolean inserted = false;
            int level = 1;
            while (!inserted) {
                level++;
                if (pData.compareTo(node.getData()) < 0) {
                    node.incrementLeftSide();
                    if (node.getLeftChild() != null) {
                        node = node.getLeftChild();
                    } else {
                        BstNode<T> insertedNode = new BstNode(node,pData, level);
                        node.setLeftChild(insertedNode);

                        while (node.getParent() != null) {
                            BstNode<T> parent = node.getParent();

                            if (parent.getNumOfSuns() == 1) {
                                if (parent.isItLeftChild(node)) {
                                    leftRotation(insertedNode);
                                    rightRotation(insertedNode);
                                } else
                                    leftRotation(node);
                            }
                            node = parent;

                        }
                        inserted = true;
                    }
                } else if (pData.compareTo(node.getData()) == 0) {
                    return false; //duplicity
                } else {
                    node.incrementRightSide();
                    if (node.getRightChild() != null) {
                        node = node.getRightChild();
                    } else {
                        BstNode<T> insertedNode = new BstNode(node,pData, level);
                        node.setRightChild(insertedNode);
                        while (node.getParent() != null) {
                            BstNode<T> parent = node.getParent();

                            if (parent.getNumOfSuns() == 1) {
                                if (parent.isItLeftChild(node)) {
                                    leftRotation(insertedNode);
                                    rightRotation(insertedNode);
                                } else
                                        leftRotation(node);
                            }
                            node = parent;

                        }
                        inserted = true;
                    }
                }
            }
        }
        size++;
        return true;
    }

    public boolean insert(T pData) { //without Rotations

        if (Root == null) {
            Root = new BstNode<T>(null, pData, 1);
        } else {
            BstNode<T> node = Root;
            boolean inserted = false;
            int level = 1;
            while (!inserted) {
                level++;
                if (pData.compareTo(node.getData()) < 0) {
                    node.incrementLeftSide();
                    if (node.getLeftChild() != null) {
                        node = node.getLeftChild();
                    } else {
                        node.setLeftChild(new BstNode(node,pData, level));
                        inserted = true;
                    }
                } else if (pData.compareTo(node.getData()) == 0) {
                    return false; //duplicity
                } else {
                    node.incrementRightSide();
                    if (node.getRightChild() != null) {
                        node = node.getRightChild();
                    } else {
                        node.setRightChild(new BstNode(node,pData, level));
                        inserted = true;
                    }
                }
            }
        }
        size++;
        return true;
    }
    public T insertWithReturn(T pData) { //with sumLeftSide/sumRightSide

        if (Root == null) {
            Root = new BstNode<T>(null, pData, 1);
        } else {
            BstNode<T> node = Root;
            boolean inserted = false;
            int level = 1;
            while (!inserted) {
                level++;
                if (pData.compareTo(node.getData()) < 0) {
                    node.incrementLeftSide();
                    if (node.getLeftChild() != null) {
                        node = node.getLeftChild();
                    } else {
                        node.setLeftChild(new BstNode(node,pData, level));
                        inserted = true;
                    }
                } else if (pData.compareTo(node.getData()) == 0) {
                    return node.getData(); //duplicity
                } else {
                    node.incrementRightSide();
                    if (node.getRightChild() != null) {
                        node = node.getRightChild();
                    } else {
                        node.setRightChild(new BstNode(node,pData, level));
                        inserted = true;
                    }
                }
            }
        }
        size++;
        return null;
    }

    public void multipleInsert(ArrayList <T> pData) {
        Collections.sort(pData);
        boolean done = false;
        boolean[] inserted = new boolean[pData.size()];

        while (!done) {
            int startIndex = -1;
            int endIndex = -1;
            int medianIndex;
            for (int i = 0; i < inserted.length; i ++) {
                if (!inserted[i] && startIndex == -1) {
                    startIndex = i;
                }
                if (!inserted[i]) {
                    endIndex = i;
                }
                if (i == inserted.length - 1)
                    break;
                if(inserted[i+1] && endIndex != -1)
                    break;
            }
            medianIndex = (startIndex + endIndex)/2;
            insert(pData.get(medianIndex));
            inserted[medianIndex] = true;

            //check is it done ?
            done = true;
            for (int i = 0; i < inserted.length; i ++) {
                if (!inserted[i]) {  //if some data not inserted, continue;
                    done = false;
                    break;
                }
            }
        }
    }


    public T getData(T pData) {
        BstNode<T> node = getNode(pData);
        if (node != null)
            return node.getData();
        else
            return null;
    }

    private BstNode<T> getNode(T pData) {

        boolean found = false;
        BstNode<T> node = Root;
            if (Root != null) {

                while (!found) {
                    if (pData.compareTo(node.getData()) < 0) {
                        if (node.getLeftChild() != null) {
                            node = node.getLeftChild();
                        } else {
                            break;
                        }
                    } else if (pData.compareTo(node.getData()) == 0) {
                        found = true;
                    } else {
                        if (node.getRightChild() != null) {
                            node = node.getRightChild();
                        } else {
                            break;
                        }
                    }

                }
            }
            if (found) {
                return node;
            } else {
                return null; // or raise exception
            }
    }
    public boolean delete(T pData) { //without rotations
        BstNode<T> node = getNode(pData);
        boolean deletedLeftChild = false;
        if (node == null)
            return false;
        BstNode<T> parent = node.getParent();
        if (parent != null)
            deletedLeftChild = parent.isItLeftChild(node);
        if (node.getLeftChild() == null && node.getRightChild() == null) {
            if (parent == null) { //it's Root
                Root = null;
            } else {
                parent.deleteChild(pData);
            }
        } else if (node.getLeftChild() != null && node.getRightChild() != null) {
            //find the best node for replacement
            //max right from the left side
            BstNode<T> repNode = node.getLeftChild();

            boolean rightSon = false;
            while(repNode.getRightChild() != null) {
                repNode = repNode.getRightChild();
                rightSon = true;
            }
            if (rightSon && repNode.getLeftChild() != null) {
                repNode.getParent().replaceChild(repNode, repNode.getLeftChild());
            }
            node.replaceMeWith(repNode);
            if (node == Root) {
                Root = repNode;
            }
        } else if (node.getRightChild() != null){
            if (parent == null) { //it's Root
                Root = node.getRightChild();
                Root.setParent(null);
                Root.setLevel(1);
            } else {
                parent.replaceChild(node, node.getRightChild());
            }
        } else {
            if (parent == null) { //it's Root
                Root = node.getLeftChild();
                Root.setParent(null);
                Root.setLevel(1);
            } else {
                parent.replaceChild(node, node.getLeftChild());
            }
        }
        size--;
        return true;

    }

    public boolean deleteWithRotations(T pData) {
        BstNode<T> node = getNode(pData);
        boolean deletedLeftChild = false;
        if (node == null)
            return false;
        BstNode<T> parent = node.getParent();
        if (parent != null)
            deletedLeftChild = parent.isItLeftChild(node);
        if (node.getLeftChild() == null && node.getRightChild() == null) {
            if (parent == null) { //it's Root
                Root = null;
            } else {
                parent.deleteChild(pData);
            }
        } else if (node.getLeftChild() != null && node.getRightChild() != null) {
            //find the best node for replacement
            //max right from the left side
            BstNode<T> repNode = node.getLeftChild();

            boolean rightSon = false;
            while(repNode.getRightChild() != null) {
                repNode = repNode.getRightChild();
                rightSon = true;
            }
            if (rightSon && repNode.getLeftChild() != null) {
                repNode.getParent().replaceChild(repNode, repNode.getLeftChild());
            }
            node.replaceMeWith(repNode);
            if (node == Root) {
                Root = repNode;
            }
        } else if (node.getRightChild() != null){
            if (parent == null) { //it's Root
                Root = node.getRightChild();
                Root.setParent(null);
                Root.setLevel(1);
            } else {
                parent.replaceChild(node, node.getRightChild());
            }
        } else {
            if (parent == null) { //it's Root
                Root = node.getLeftChild();
                Root.setParent(null);
                Root.setLevel(1);
            } else {
                parent.replaceChild(node, node.getLeftChild());
            }
        }
        //added rotations
        while (parent != null && parent.getParent() != null) {
            if (parent.getNumOfSuns() == 1 && parent.getParent().getNumOfSuns() == 1) {

                if (parent.getParent().isItLeftChild(parent) && !deletedLeftChild) //it's left son
                    rightRotation(parent);
                else if(!parent.getParent().isItLeftChild(parent) && deletedLeftChild) {
                    leftRotation(parent);
                } else if(!parent.getParent().isItLeftChild(parent) && !deletedLeftChild) {
                    node = parent.getLeftChild();
                    rightRotation(node);
                    leftRotation(node);
                } else {
                    node = parent.getRightChild();
                    leftRotation(node);
                    rightRotation(node);
                }
            }
            parent = parent.getParent();
        }
        size--;
        return true;

    }

    public boolean rightRotation(BstNode<T> pNode) {
        BstNode<T> node = pNode;
        if (node == null || node.getParent() == null)
            return false;

        updateSumsBeforeRightRotation(node);

        BstNode<T> parent = node.getParent();
        if (parent != Root) {
            parent.getParent().replaceChild(parent, node);
        }else {
            node.setParent(null);
            Root = node;
        }
        parent.replaceChild(node, node.getRightChild());
        if (node.getRightChild() != null)
            node.replaceChild(node.getRightChild(), parent);
        else {
            node.setRightChild(parent);
            parent.setParent(node);
        }
        updateChildLevel(node);
        return true;
    }
    public boolean leftRotation(BstNode<T> pNode) {
        BstNode<T> node = pNode;
        if (node == null || node.getParent() == null)
            return false;

        updateSumsBeforeLeftRotation(node);

        BstNode<T> parent = node.getParent();
        if (parent != Root) {
            parent.getParent().replaceChild(parent, node);
        }else {
            node.setParent(null);
            Root = node;
        }
        parent.replaceChild(node, node.getLeftChild());
        if (node.getLeftChild() != null)
            node.replaceChild(node.getLeftChild(), parent);
        else {
            node.setLeftChild(parent);
            parent.setParent(node);
        }
        updateChildLevel(node);
        return true;
    }
    public void updateSumsBeforeRightRotation(BstNode<T> pNode) {
        pNode.getParent().setSumLeftSide(pNode.getSumRightSide());
        pNode.setSumRightSide(pNode.getParent().getSumRightSide() + pNode.getParent().getSumLeftSide() + 1);
    }
    public void updateSumsBeforeLeftRotation(BstNode<T> pNode) {
        pNode.getParent().setSumRightSide(pNode.getSumLeftSide());
        pNode.setSumLeftSide(pNode.getParent().getSumRightSide() + pNode.getParent().getSumLeftSide() + 1);
    }

    public BstNode<T> getMinNode(BstNode<T> startNode) {
        BstNode<T> node = startNode;
        while(node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }
    public BstNode<T> getMaxNode(BstNode<T> startNode) {
        BstNode<T> node = startNode;
        while(node.getRightChild() != null) {
            node = node.getRightChild();
        }
        return node;
    }

    public T getMin() {
        if (Root == null) {
            return null;
        } else {
            BstNode<T> node = Root;
            while(node.getLeftChild() != null) {
                node = node.getLeftChild();
            }
            return node.getData();
        }
    }
    public T getMax() {
        if (Root == null) {
            return null;
        } else {
            BstNode<T> node = Root;
            while(node.getRightChild() != null) {
                node = node.getRightChild();
            }
            return node.getData();
        }
    }
    public BstNode<T> getRoot() {
        return Root;
    }

    public void inorder() {
        BstNode<T> node = Root;
        clearVisited();
        if (Root == null)
            return;
        while (true) {

            if (!node.visited) {

                while (node.getLeftChild() != null) {
                    if (node.getLeftChild().visited)
                        break;
                    node = node.getLeftChild();
                }
                System.out.println(node.getData() + "  ");
                node.visited = true;

            }

            boolean goHigher = false;
            if (node.getRightChild() != null) {
                if (!node.getRightChild().visited)
                    node = node.getRightChild();
                else
                    goHigher = true;
            } else {
                goHigher = true;
            }
            if (goHigher) {
                try {
                    while (node.getParent().visited)
                        node = node.getParent();
                    node = node.getParent();
                } catch (NullPointerException e) {
                    return;
                }
            }
        }
    }

    public ArrayList<String> inorderRetArrayListStr() {
        clearVisited();
        BstNode<T> node = Root;
        ArrayList<String> result = new ArrayList<String>();
        if (Root == null)
            return result;
        while (true) {

            if (!node.visited) {

                while (node.getLeftChild() != null) {
                    if (node.getLeftChild().visited)
                        break;
                    node = node.getLeftChild();
                }
                result.add(node.getData().toString());
                node.visited = true;

            }

            boolean goHigher = false;
            if (node.getRightChild() != null) {
                if (!node.getRightChild().visited)
                    node = node.getRightChild();
                else
                    goHigher = true;
            } else {
                goHigher = true;
            }
            if (goHigher) {
                try {
                    while (node.getParent().visited)
                        node = node.getParent();
                    node = node.getParent();
                } catch (NullPointerException e) {
                    return result;
                }
            }
        }
    }
    public ArrayList<T> inorderRetArrayListObj() {
        clearVisited();
        BstNode<T> node = Root;
        ArrayList<T> result = new ArrayList<T>();
        if (Root == null)
            return result;
        while (true) {

            if (!node.visited) {

                while (node.getLeftChild() != null) {
                    if (node.getLeftChild().visited)
                        break;
                    node = node.getLeftChild();
                }
                result.add(node.getData());
                node.visited = true;

            }
            boolean goHigher = false;
            if (node.getRightChild() != null) {
                if (!node.getRightChild().visited)
                    node = node.getRightChild();
                else
                    goHigher = true;
            } else {
                goHigher = true;
            }
            if (goHigher) {
                try {
                    while (node.getParent().visited)
                        node = node.getParent();
                    node = node.getParent();
                } catch (NullPointerException e) {
                    return result;
                }
            }
        }
    }

    public ArrayList<T> intervalSearch(T pData, T pData2) { //pData - ma len od ; pData2 - ma od + do
        ArrayList<T> result = new ArrayList<>();
        clearVisited();
        BstNode<T> node = Root;
        if (Root == null) {
            return result;
        } else {

            boolean found = false;

            while (!found) {

                if (pData.compareTo(node.getData()) < 0) {
                    //node.incrementLeftSide();
                    if (node.getLeftChild() != null) {
                        node = node.getLeftChild();
                    } else {
                        break;
                    }
                } else if (pData.compareTo(node.getData()) == 0) {
                    break; //key found
                } else {
                    //node.incrementRightSide();
                    if (node.getRightChild() != null) {
                        node = node.getRightChild();
                    } else {
                        break;
                    }
                }
            }
        }

        if(node.getData().compareTo(pData2) == 0)
            result.add(node.getData());
        if (node.getRightChild() != null) {
            result.addAll(subTreeSearch(node.getRightChild(), pData2));
        }


        while (true) {
            if(node.getParent() != null) {
                if (node.getParent().isItRightChild(node)) {
                    node = node.getParent();
                    if (node.getData().compareTo(pData2) == 0)
                        result.add(node.getData());
                } else {
                    node = node.getParent();
                    if (node.getData().compareTo(pData2) == 0)
                        result.add(node.getData());
                    if (node.getRightChild() != null) {
                        result.addAll(subTreeSearch(node.getRightChild(), pData2));
                    }
                }
            }
            if(node.getParent() == null) {
                break;
            }
        }
            return  result;

    }

    public ArrayList<T> subTreeSearch(BstNode<T> pNode, T comparator) {
        clearVisited();
        BstNode<T> node = pNode;
        BstNode<T> nodeExit = node.getParent();
        ArrayList<T> result = new ArrayList<>();

        while (true) {
            if (node == nodeExit)
                break;
            if (!node.visited) {

                if (node.getData().compareTo(comparator) == 0)
                    result.add(node.getData());
                node.visited = true;
            }
            if (node.getLeftChild() != null && !node.getLeftChild().visited)
                node = node.getLeftChild();
            else if (node.getRightChild() != null && !node.getRightChild().visited) {
                node = node.getRightChild();
            } else {
                node = node.getParent();
            }
        }
        return result;
    }


    public void clearVisited() {
        BstNode<T> node = Root;

        if (Root == null)
            return;

        boolean vis = Root.cleanUpVisited;
        while (true) {
            if ( vis == node.cleanUpVisited) {

                while (node.getLeftChild() != null) {
                    if (!vis == node.getLeftChild().cleanUpVisited)
                        break;
                    node = node.getLeftChild();
                }
                node.cleanUpVisited = !vis;
                node.visited = false;

            } else { return;}

            boolean goHigher = false;
            if (node.getRightChild() != null) {
                if (vis == node.getRightChild().cleanUpVisited)
                    node = node.getRightChild();
                else
                    goHigher = true;
            } else {
                goHigher = true;
            }
            if (goHigher) {
                try {
                    while (!vis == node.getParent().cleanUpVisited)
                        node = node.getParent();
                    node = node.getParent();
                } catch (NullPointerException e) {
                    return;
                }
            }
        }
    }

    public void inorder2() { //working but deleting entries
        if (Root == null)
            return;

        BstNode<T> node = Root;
        BstNode<T> parent = null;

        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }

        while (true) {
            if (node == Root && node.getRightChild() == null && node.getLeftChild() == null) {
                return;
            }
            if (node.getLeftChild() == null) {
                System.out.println(node.getData() + " ");
                boolean moved = false;
                if (node.getRightChild() != null) {
                    node = node.getRightChild();
                    moved = true;
                }
                if (node != Root && !moved){

                    parent = node.getParent();
                    while(parent.isItRightChild(node)) {
                        node = parent;
                        parent = parent.getParent();
                        node.setLeftChild(null);
                        node.setRightChild(null);
                        if(parent.getParent() == null) { // it's root
                            break;
                        }
                    }
                    parent.deleteChild(node.getData());
                    node = parent;
                }

            } else {
                node = node.getLeftChild();
            }
        }
    }
    public int getMaxLevel() {
        BstNode<T> node  = getMaxLevelNode();
        if ( node != null)
            return node.getLevel();
        else
            return 0;
    }
    public void inorderTmakeBalance() {
        clearVisited();
        BstNode<T> node = Root;
        if (Root == null)
            return ;
        while (true) {

            if (!node.visited) {

                while (node.getLeftChild() != null) {
                    if (node.getLeftChild().visited)
                        break;
                    node = node.getLeftChild();
                }
                callTmakeBalance(node);
                node.visited = true;

            }
            boolean goHigher = false;
            if (node.getRightChild() != null) {
                if (!node.getRightChild().visited)
                    node = node.getRightChild();
                else
                    goHigher = true;
            } else {
                goHigher = true;
            }
            if (goHigher) {
                try {
                    while (node.getParent().visited)
                        node = node.getParent();
                    node = node.getParent();
                } catch (NullPointerException e) {
                    return;
                }
            }
        }

    }

    public ArrayList<T> inorderSearch( T data) {
        clearVisited();
        BstNode<T> node = Root;
        ArrayList<T> result = new ArrayList<T>();
        if (Root == null)
            return result;
        while (true) {

            if (!node.visited) {

                while (node.getLeftChild() != null) {
                    if (node.getLeftChild().visited)
                        break;
                    node = node.getLeftChild();
                }
                if (node.getData().compareTo(data) == 0)
                    result.add(node.getData());
                node.visited = true;

            }
            boolean goHigher = false;
            if (node.getRightChild() != null) {
                if (!node.getRightChild().visited)
                    node = node.getRightChild();
                else
                    goHigher = true;
            } else {
                goHigher = true;
            }
            if (goHigher) {
                try {
                    while (node.getParent().visited)
                        node = node.getParent();
                    node = node.getParent();
                } catch (NullPointerException e) {
                    return result;
                }
            }
        }

    }


    private void callTmakeBalance(BstNode node) {
        Method m = null;
        try {
            m = node.getData().getClass().getMethod("makeBalance", null);
        } catch (NoSuchMethodException | SecurityException e) {
        }

        if (m != null) {
            try {
                m.invoke(node.getData(), null);
            } catch (InvocationTargetException e ) {

            } catch (IllegalAccessException e2) {

            }
        }

    }

    public void makeBalance() {
        boolean isBalance = false;
        int stableLevels = (int) Math.ceil(Math.log(size+1) / Math.log(2)) ;
        int allowed_difference = 1;
        if (Math.log(size+1) / Math.log(2) == stableLevels) {
            allowed_difference = 0;
        }

        boolean leftSideBalanced = false;
        BstNode<T> node = Root;
        clearVisited();
        while (!isBalance) {
            BstNode<T> movedNode;
            if (node.getSumLeftSide() > node.getSumRightSide() + allowed_difference) {
                movedNode = getMaxNode(node.getLeftChild());
            } else if (node.getSumLeftSide()  + allowed_difference < node.getSumRightSide()) {
                movedNode = getMinNode(node.getRightChild());
            } else { //take another node
                node.visited = true;
                if (node.getLeftChild() != null && !node.getLeftChild().visited) {
                    if (node == Root && leftSideBalanced) {
                        return;
                    }
                    node = node.getLeftChild();
                } else if (node.getRightChild() != null && !node.getRightChild().visited) {
                    if (node == Root) {
                        leftSideBalanced = true;
                    }
                    node = node.getRightChild();
                }else
                    node = node.getParent();
                if (node == null)
                    return;
                continue;
            }
            moveNodeToPlace(movedNode, node);
            clearVisited();
            node = movedNode;
        }
    }
    public void moveNodeToPlace(BstNode<T> pNode, BstNode<T> endNode) {
        BstNode<T> node = pNode;
        int endLevel = endNode.getLevel();
        while (node.getLevel() != endLevel ) {
            BstNode<T> parent = node.getParent();
            if(parent.isItLeftChild(node))
                rightRotation(node);
            else
                leftRotation(node);
        }
    }
    public BstNode<T> getMaxLevelNode() {
        clearVisited();
        BstNode<T> returnNode = Root;
        BstNode<T> node = Root;

        if (Root == null)
            return null;
        while (true) {
            if (!node.visited) {

                while (node.getLeftChild() != null) {
                    if (node.getLeftChild().visited)
                        break;
                    node = node.getLeftChild();
                }
                node.visited = true;
                if (returnNode.getLevel() < node.getLevel())
                    returnNode = node;
            }

            boolean goHigher = false;
            if (node.getRightChild() != null) {
                if (!node.getRightChild().visited)
                    node = node.getRightChild();
                else
                    goHigher = true;
            } else {
                goHigher = true;
            }
            if (goHigher) {
                try {
                    while (node.getParent().visited) {
                        node = node.getParent();
                    }
                    node = node.getParent();
                } catch (NullPointerException e) {
                    return returnNode;
                }
            }
        }
    }
    public void updateChildLevel(BstNode<T> pNode) {
        clearVisited();

        BstNode<T> node = pNode;
        BstNode<T> nodeExit = pNode.getParent();

        while (true) {
            if (node == nodeExit)
                return;
            if (!node.visited) {

                if (node == Root)
                    node.setLevel(1);
                else
                    node.setLevel(node.getParent().getLevel() + 1);
                node.visited = true;
            }
            if (node.getLeftChild() != null && !node.getLeftChild().visited)
                node = node.getLeftChild();
            else if (node.getRightChild() != null && !node.getRightChild().visited) {
                node = node.getRightChild();
            } else {
                node = node.getParent();
            }

        }
    }
}
