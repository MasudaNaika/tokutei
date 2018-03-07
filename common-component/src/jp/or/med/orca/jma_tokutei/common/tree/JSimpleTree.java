package jp.or.med.orca.jma_tokutei.common.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class JSimpleTree extends JTree{

	public JSimpleTree(Vector vc){
		super(vc);
	}
	/**
     * 指定されたノード配下のノードをリストにして返す。
     */
    public ArrayList getAllNode(DefaultMutableTreeNode node) {
        assert node != null;
        ArrayList result = new ArrayList();
        result.add(node);
        for (int i=0; i<node.getChildCount(); i++) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt(i);
            result.addAll(getAllNode(n));
        }
        return result;
    }

    /** 選択されたノードに登録されているオブジェクトを返す。*/
    public Object getSelectionNodeObject() {
        TreePath path = getSelectionPath();
        if (path == null) return null;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        return node.getUserObject();
    }

    /**
     * 指定されたノードが何列目かを返す。
     * 見つからなかった場合、(-1)を返す。
     */
    public int getRow(DefaultMutableTreeNode node) {
        for (int r=0; r<getRowCount(); r++) {
            Object obj = getPathForRow(r).getLastPathComponent();
            if (obj == node) return r;
        }
        return -1;  // 見つからなかった
    }
}
