// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.beans.Beans;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import org.openswing.swing.client.BaseInputControl;
import org.openswing.swing.client.InputControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.domains.java.DomainPair;
import org.openswing.swing.internationalization.java.Resources;
import org.openswing.swing.logger.client.Logger;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.util.client.SearchControl;
import org.openswing.swing.util.client.SearchWindowManager;

public class ExtendedListControl extends BaseInputControl
    implements InputControl, SearchControl
{
    class CheckBoxLabel extends JLabel
    {

        public void setSelected(boolean flag)
        {
            sel = flag;
            repaint();
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.translate(getWidth() / 2 - 6, getHeight() / 2 - 5);
            BasicGraphicsUtils.drawLoweredBezel(g, 0, 0, 12, 12, Color.darkGray, Color.black, Color.white, Color.gray);
            if(sel)
            {
                if(!isEnabled())
                    g.setColor(Color.GRAY);
                else
                    g.setColor(Color.black);
                g.drawLine(3, 5, 5, 7);
                g.drawLine(3, 6, 5, 8);
                g.drawLine(3, 7, 5, 9);
                g.drawLine(6, 6, 9, 3);
                g.drawLine(6, 7, 9, 4);
                g.drawLine(6, 8, 9, 5);
            }
        }

        private boolean sel;
//        final ExtendedListControl this$0;
//
//        CheckBoxLabel()
//        {
//            this$0 = ExtendedListControl.this;
//            super();
//        }
    }

    class ListControlCellRenderer extends DefaultListCellRenderer
    {

        public Component getListCellRendererComponent(JList jlist, Object obj, int i, boolean flag, boolean flag1)
        {
            Component component = super.getListCellRendererComponent(jlist, obj, i, flag && !isShowCheckBoxes(), flag1);
            if(isShowCheckBoxes())
            {
                checkBox.setEnabled(isEnabled());
                checkBox.setSelected(flag);
            }
            return panel;
        }

        private CheckBoxLabel checkBox;
        private JPanel panel;
        final ExtendedListControl this$0;

        public ListControlCellRenderer()
        {
            this$0 = ExtendedListControl.this;
//            super();
            checkBox = new CheckBoxLabel();
            panel = new JPanel();
            checkBox.setOpaque(false);
            panel.setOpaque(false);
            panel.setLayout(new BorderLayout(0, 0));
            panel.add(this, "Center");
            if(isShowCheckBoxes())
            {
                checkBox.setSize(14, 14);
                checkBox.setPreferredSize(new Dimension(14, 14));
                panel.add(checkBox, "Before");
                if(list.getSelectionMode() == 1)
                    list.setSelectionMode(2);
            }
        }
    }


    public ExtendedListControl()
    {
        firstTime = false;
        list = new JList();
        domain = null;
        domainId = null;
        model = new DefaultListModel();
        nullAsDefaultValue = false;
        showCheckBoxes = false;
        translateItemDescriptions = true;
        setLayout(new GridBagLayout());
        add(new JScrollPane(list), new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
        setOpaque(false);
        list.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent keyevent)
            {
                KeyEvent _tmp = keyevent;
                if(keyevent.getKeyCode() != 3)
                {
                    KeyEvent _tmp1 = keyevent;
                    if(keyevent.getKeyCode() != 8)
                    {
// del s.inoue 2012/07/04
//                        KeyEvent _tmp2 = keyevent;
//                        if(keyevent.getKeyCode() != 127)
//                            break MISSING_BLOCK_LABEL_43;
                    }
                }
                list.setSelectedIndex(-1);
            }

            final ExtendedListControl this$0;


            {
                this$0 = ExtendedListControl.this;
//                super();
            }
        });
        list.setSelectionForeground((Color)UIManager.get("TextField.foreground"));
        initListeners();
        getBindingComponent().addFocusListener(new FocusAdapter() {

            public void focusGained(FocusEvent focusevent)
            {
            	// eidt s.inoue 2012/07/04
                if(ClientSettings.VIEW_BACKGROUND_SEL_COLOR && isEnabled())
                    getBindingComponent().setBackground(ClientSettings.GRID_SELECTION_BACKGROUND);

            }

            final ExtendedListControl this$0;


            {
                this$0 = ExtendedListControl.this;
//                super();
            }
        });
        new SearchWindowManager(this);
        if(ClientSettings.TEXT_ORIENTATION != null)
            setComponentOrientation(ClientSettings.TEXT_ORIENTATION);
    }

    public final void addNotify()
    {
        super.addNotify();
        if(!firstTime)
        {
            firstTime = true;
            list.setCellRenderer(new ListControlCellRenderer());
        }
    }

    public final void setDomainId(String s)
    {
        domainId = s;
        if(Beans.isDesignTime())
        {
            return;
        } else
        {
            Domain domain1 = ClientSettings.getInstance().getDomain(s);
            setDomain(domain1);
            return;
        }
    }

    public final void setDomain(Domain domain1)
    {
        domain = domain1;
        model.removeAllElements();
        if(domain1 != null)
        {
            DomainPair adomainpair[] = domain1.getDomainPairList();
            for(int i = 0; i < adomainpair.length; i++)
                if(translateItemDescriptions)
                    model.addElement(ClientSettings.getInstance().getResources().getResource(adomainpair[i].getDescription()));
                else
                    model.addElement(adomainpair[i].getDescription());

            list.setModel(model);
            list.revalidate();
            list.setSelectedIndex(-1);
        }
    }

    public final String getDomainId()
    {
        return domainId;
    }

    public final void setValue(Object obj)
    {
        if(list.getSelectionMode() == 0)
        {
            if(obj == null)
                list.setSelectedIndex(-1);
            if(domain == null)
                return;
            DomainPair domainpair = domain.getDomainPair(obj);
            if(domainpair != null)
                if(translateItemDescriptions)
                    list.setSelectedValue(ClientSettings.getInstance().getResources().getResource(domainpair.getDescription()), true);
                else
                    list.setSelectedValue(domainpair.getDescription(), true);
        } else
        {
            if(obj == null || (obj instanceof java.util.List) && ((java.util.List)obj).size() == 0)
            {
                list.getSelectionModel().clearSelection();
                return;
            }
            if(domain == null)
                return;
            if(obj instanceof java.util.List)
            {
                java.util.List list1 = (java.util.List)obj;
                list.getSelectionModel().clearSelection();
                for(int i = 0; i < list1.size(); i++)
                {
                    for(int j = 0; j < domain.getDomainPairList().length; j++)
                        if(domain.getDomainPairList()[j].getCode().equals(list1.get(i)))
                            list.getSelectionModel().addSelectionInterval(j, j);

                }

            } else
            {
                Logger.error(getClass().getName(), "setValue", "You must specify a java.util.List argument type", null);
            }
        }
    }

    public final void setSelectedIndex(int i)
    {
        list.setSelectedIndex(i);
        try
        {
            list.scrollRectToVisible(list.getCellBounds(i, i));
        }
        catch(Exception exception) { }
    }

    public final void setSelectedIndices(int ai[])
    {
        list.setSelectedIndices(ai);
        try
        {
            list.scrollRectToVisible(list.getCellBounds(ai[0], ai[0]));
        }
        catch(Exception exception) { }
    }

    public final int getFixedCellHeight()
    {
        return list.getFixedCellHeight();
    }

    public final void setFixedCellHeight(int i)
    {
        list.setFixedCellHeight(i);
    }

    public final int getFixedCellWidth()
    {
        return list.getFixedCellWidth();
    }

    public final void setFixedCellWidth(int i)
    {
        list.setFixedCellWidth(i);
    }

    public final int getVisibleRowCount()
    {
        return list.getVisibleRowCount();
    }

    public final void setVisibleRowCount(int i)
    {
        list.setVisibleRowCount(i);
    }

    public final int getLayoutOrientation()
    {
        return list.getLayoutOrientation();
    }

    public final void setLayoutOrientation(int i)
    {
        list.setLayoutOrientation(i);
    }

    public final void setSelectionMode(int i)
    {
        list.setSelectionMode(i);
    }

    public final int getSelectionMode()
    {
        return list.getSelectionMode();
    }

    public final void setValueIsAdjusting(boolean flag)
    {
        list.setValueIsAdjusting(flag);
    }

    public final boolean getValueIsAdjusting()
    {
        return list.getValueIsAdjusting();
    }

    public Color getSelectionForeground()
    {
        return list.getSelectionForeground();
    }

    public final void setSelectionForeground(Color color)
    {
        list.setSelectionForeground(color);
    }

    public final Color getSelectionBackground()
    {
        return list.getSelectionBackground();
    }

    public void setSelectionBackground(Color color)
    {
        list.setSelectionBackground(color);
    }

    public final void addListSelectionListener(ListSelectionListener listselectionlistener)
    {
        list.addListSelectionListener(listselectionlistener);
    }

    public final void removeListSelectionListener(ListSelectionListener listselectionlistener)
    {
        list.removeListSelectionListener(listselectionlistener);
    }

    public final Object getValue()
    {
        if(list.getSelectionMode() == 0)
        {
            if(domain == null)
                return null;
            DomainPair adomainpair[] = domain.getDomainPairList();
            if(list.getSelectedIndex() == -1)
                return null;
            else
                return adomainpair[list.getSelectedIndex()].getCode();
        }
        if(domain == null)
            return new ArrayList();
        DomainPair adomainpair1[] = domain.getDomainPairList();
        ArrayList arraylist = new ArrayList();
        int ai[] = list.getSelectedIndices();
        for(int i = 0; i < ai.length; i++)
            arraylist.add(adomainpair1[ai[i]].getCode());

        return arraylist;
    }

    public void setEnabled(boolean flag)
    {
        list.setEnabled(flag);
        list.setFocusable(flag || ClientSettings.DISABLED_INPUT_CONTROLS_FOCUSABLE);
        if(flag)
        {
            list.setBackground((Color)UIManager.get("TextField.background"));
            list.setSelectionBackground(ClientSettings.BACKGROUND_SEL_COLOR);
        } else
        {
            list.setBackground((Color)UIManager.get("TextField.inactiveBackground"));
            try
            {
                list.setSelectionBackground(new Color(list.getBackground().getRed() - 10, list.getBackground().getGreen() - 10, list.getBackground().getBlue() - 10));
            }
            catch(Exception exception)
            {
                list.setSelectionBackground(Color.lightGray);
            }
        }
    }

    public final boolean isEnabled()
    {
        return list.isEnabled();
//        Exception exception;
//        exception;
//        return false;
    }

    public JComponent getBindingComponent()
    {
        return list;
    }

    public final void addFocusListener(FocusListener focuslistener)
    {
        try
        {
            list.addFocusListener(focuslistener);
        }
        catch(Exception exception) { }
    }

    public final void removeFocusListener(FocusListener focuslistener)
    {
        try
        {
            list.removeFocusListener(focuslistener);
        }
        catch(Exception exception) { }
    }

    public final void addMouseListener(MouseListener mouselistener)
    {
        try
        {
            list.addMouseListener(mouselistener);
        }
        catch(Exception exception) { }
    }

    public final void removeMouseListener(MouseListener mouselistener)
    {
        try
        {
            list.removeMouseListener(mouselistener);
        }
        catch(Exception exception) { }
    }

    public final boolean isNullAsDefaultValue()
    {
        return nullAsDefaultValue;
    }

    public final void setNullAsDefaultValue(boolean flag)
    {
        nullAsDefaultValue = flag;
    }

    public final Domain getDomain()
    {
        return domain;
    }

    public final int getSelectedIndex()
    {
        return list.getSelectedIndex();
    }

    public final int getRowCount()
    {
        return list.getModel().getSize();
    }

    public final String getValueAt(int i)
    {
        return list.getModel().getElementAt(i) != null ? list.getModel().getElementAt(i).toString() : "";
    }

    public final JComponent getComponent()
    {
        return list;
    }

    public final boolean isReadOnly()
    {
        return isEnabled();
    }

    public final boolean isShowCheckBoxes()
    {
        return showCheckBoxes;
    }

    public final void setShowCheckBoxes(boolean flag)
    {
        showCheckBoxes = flag;
    }

    public final boolean disableListener()
    {
        return false;
    }

    public final boolean isTranslateItemDescriptions()
    {
        return translateItemDescriptions;
    }

    public final void setTranslateItemDescriptions(boolean flag)
    {
        translateItemDescriptions = flag;
    }

    public final int search(String s)
    {
        return -1;
    }

    public final void setTextOrientation(ComponentOrientation componentorientation)
    {
        list.setComponentOrientation(componentorientation);
    }

    public final ComponentOrientation getTextOrientation()
    {
        return list.getComponentOrientation();
//        Exception exception;
//        exception;
//        return null;
    }

    private boolean firstTime;
    private JList list;
    private Domain domain;
    private String domainId;
    private DefaultListModel model;
    private boolean nullAsDefaultValue;
    private boolean showCheckBoxes;
    private boolean translateItemDescriptions;


}
