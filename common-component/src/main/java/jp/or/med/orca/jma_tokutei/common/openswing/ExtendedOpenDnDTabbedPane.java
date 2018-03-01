// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ExtendedOpenDnDTabbedPane extends JTabbedPane
{
    class GhostGlassPane extends JPanel
    {

        public void setImage(BufferedImage bufferedimage)
        {
            draggingGhost = bufferedimage;
        }

        public void setPoint(Point point)
        {
            location = point;
        }

        public void paintComponent(Graphics g)
        {
            Graphics2D graphics2d = (Graphics2D)g;
            graphics2d.setComposite(composite);
            if(isPaintScrollArea() && getTabLayoutPolicy() == 1)
            {
                graphics2d.setPaint(Color.RED);
                graphics2d.fill(ExtendedOpenDnDTabbedPane.rBackward);
                graphics2d.fill(ExtendedOpenDnDTabbedPane.rForward);
            }
            if(draggingGhost != null)
            {
                double d = location.getX() - (double)draggingGhost.getWidth(this) / 2D;
                double d1 = location.getY() - (double)draggingGhost.getHeight(this) / 2D;
                graphics2d.drawImage(draggingGhost, (int)d, (int)d1, null);
            }
            if(dragTabIndex >= 0)
            {
                graphics2d.setPaint(lineColor);
                graphics2d.fill(lineRect);
            }
        }

        private final AlphaComposite composite = AlphaComposite.getInstance(3, 0.5F);
        private Point location;
        private BufferedImage draggingGhost;
        final ExtendedOpenDnDTabbedPane this$0;

        public GhostGlassPane()
        {
            this$0 = ExtendedOpenDnDTabbedPane.this;
//            super();
            location = new Point(0, 0);
            draggingGhost = null;
            setOpaque(false);
        }
    }

    class CDropTargetListener
        implements DropTargetListener
    {

        public void dragEnter(DropTargetDragEvent droptargetdragevent)
        {
            if(isDragAcceptable(droptargetdragevent))
                droptargetdragevent.acceptDrag(droptargetdragevent.getDropAction());
            else
                droptargetdragevent.rejectDrag();
        }

        public void dragExit(DropTargetEvent droptargetevent)
        {
        }

        public void dropActionChanged(DropTargetDragEvent droptargetdragevent)
        {
        }

        public void dragOver(DropTargetDragEvent droptargetdragevent)
        {
            Point point = droptargetdragevent.getLocation();
            if(getTabPlacement() == 1 || getTabPlacement() == 3)
                initTargetLeftRightLine(getTargetTabIndex(point));
            else
                initTargetTopBottomLine(getTargetTabIndex(point));
            if(hasGhost())
                glassPane.setPoint(point);
            if(!_glassPt.equals(point))
                glassPane.repaint();
            _glassPt = point;
            autoScrollTest(point);
        }

        public void drop(DropTargetDropEvent droptargetdropevent)
        {
            if(isDropAcceptable(droptargetdropevent))
            {
                convertTab(dragTabIndex, getTargetTabIndex(droptargetdropevent.getLocation()));
                droptargetdropevent.dropComplete(true);
            } else
            {
                droptargetdropevent.dropComplete(false);
            }
            repaint();
        }

        private boolean isDragAcceptable(DropTargetDragEvent droptargetdragevent)
        {
            Transferable transferable = droptargetdragevent.getTransferable();
            if(transferable == null)
                return false;
            DataFlavor adataflavor[] = droptargetdragevent.getCurrentDataFlavors();
            return transferable.isDataFlavorSupported(adataflavor[0]) && dragTabIndex >= 0;
        }

        private boolean isDropAcceptable(DropTargetDropEvent droptargetdropevent)
        {
            Transferable transferable = droptargetdropevent.getTransferable();
            if(transferable == null)
                return false;
            DataFlavor adataflavor[] = transferable.getTransferDataFlavors();
            return transferable.isDataFlavorSupported(adataflavor[0]) && dragTabIndex >= 0;
        }

        private Point _glassPt;
        final ExtendedOpenDnDTabbedPane this$0;

        CDropTargetListener()
        {
            this$0 = ExtendedOpenDnDTabbedPane.this;
//            super();
            _glassPt = new Point();
        }
    }


    private void clickArrowButton(String s)
    {
        ActionMap actionmap = getActionMap();
        if(actionmap != null)
        {
            Action action = actionmap.get(s);
            if(action != null && action.isEnabled())
                action.actionPerformed(new ActionEvent(this, 1001, null, 0L, 0));
        }
    }

    private void autoScrollTest(Point point)
    {
        Rectangle rectangle = getTabAreaBounds();
        int i = getTabPlacement();
        if(i == 1 || i == 3)
        {
            rBackward.setBounds(rectangle.x, rectangle.y, rwh, rectangle.height);
            rForward.setBounds((rectangle.x + rectangle.width) - rwh - buttonsize, rectangle.y, rwh + buttonsize, rectangle.height);
        } else
        if(i == 2 || i == 4)
        {
            rBackward.setBounds(rectangle.x, rectangle.y, rectangle.width, rwh);
            rForward.setBounds(rectangle.x, (rectangle.y + rectangle.height) - rwh - buttonsize, rectangle.width, rwh + buttonsize);
        }
        rBackward = SwingUtilities.convertRectangle(getParent(), rBackward, glassPane);
        rForward = SwingUtilities.convertRectangle(getParent(), rForward, glassPane);
        if(rBackward.contains(point))
            clickArrowButton("scrollTabsBackwardAction");
        else
        if(rForward.contains(point))
            clickArrowButton("scrollTabsForwardAction");
    }

    public ExtendedOpenDnDTabbedPane()
    {
        dragTabIndex = -1;
        hasGhost = true;
        isPaintScrollArea = true;
        final DragSourceListener dsl = new DragSourceListener() {

            public void dragEnter(DragSourceDragEvent dragsourcedragevent)
            {
                dragsourcedragevent.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
            }

            public void dragExit(DragSourceEvent dragsourceevent)
            {
                dragsourceevent.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
                lineRect.setRect(0.0D, 0.0D, 0.0D, 0.0D);
                glassPane.setPoint(new Point(-1000, -1000));
                glassPane.repaint();
            }

            public void dragOver(DragSourceDragEvent dragsourcedragevent)
            {
                Point point = dragsourcedragevent.getLocation();
                SwingUtilities.convertPointFromScreen(point, glassPane);
                int i = getTargetTabIndex(point);
                if(getTabAreaBounds().contains(point) && i >= 0 && i != dragTabIndex && i != dragTabIndex + 1)
                {
                    dragsourcedragevent.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
                    glassPane.setCursor(DragSource.DefaultMoveDrop);
                } else
                {
                    dragsourcedragevent.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
                    glassPane.setCursor(DragSource.DefaultMoveNoDrop);
                }
            }

            public void dragDropEnd(DragSourceDropEvent dragsourcedropevent)
            {
                lineRect.setRect(0.0D, 0.0D, 0.0D, 0.0D);
                dragTabIndex = -1;
                glassPane.setVisible(false);
                if(hasGhost())
                {
                    glassPane.setVisible(false);
                    glassPane.setImage(null);
                }
            }

            public void dropActionChanged(DragSourceDragEvent dragsourcedragevent)
            {
            }

            final ExtendedOpenDnDTabbedPane this$0;


            {
                this$0 = ExtendedOpenDnDTabbedPane.this;
//                super();
            }
        };
        final Transferable t = new Transferable() {

            public Object getTransferData(DataFlavor dataflavor)
            {
                return ExtendedOpenDnDTabbedPane.this;
            }

            public DataFlavor[] getTransferDataFlavors()
            {
                DataFlavor adataflavor[] = new DataFlavor[1];
                adataflavor[0] = FLAVOR;
                return adataflavor;
            }

            public boolean isDataFlavorSupported(DataFlavor dataflavor)
            {
                return dataflavor.getHumanPresentableName().equals("test");
            }

            private final DataFlavor FLAVOR = new DataFlavor("application/x-java-jvm-local-objectref", "test");
            final ExtendedOpenDnDTabbedPane this$0;


            {
                this$0 = ExtendedOpenDnDTabbedPane.this;
//                super();
            }
        };

// add s.inoue 2012/11/06
        final DragGestureListener dgl = new DragGestureListener() {
            @Override public void dragGestureRecognized(DragGestureEvent e) {
                if(getTabCount()<=1) return;
                Point tabPt = e.getDragOrigin();
                dragTabIndex = indexAtLocation(tabPt.x, tabPt.y);
                //"disabled tab problem".
                if(dragTabIndex<0 || !isEnabledAt(dragTabIndex)) return;
                initGlassPane(e.getComponent(), e.getDragOrigin());
                try{
                    e.startDrag(DragSource.DefaultMoveDrop, t, dsl);
                }catch(InvalidDnDOperationException idoe) {
                    idoe.printStackTrace();
                }
            }
        };
        new DropTarget(glassPane, DnDConstants.ACTION_COPY_OR_MOVE, new CDropTargetListener(), true);
        new DragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, dgl);
// del s.inoue 2012/07/04
//        DragGestureListener draggesturelistener = new DragGestureListener() {
//
//            public void dragGestureRecognized(DragGestureEvent draggestureevent)
//            {
//                if(getTabCount() <= 1)
//                    return;
//                Point point = draggestureevent.getDragOrigin();
//                dragTabIndex = indexAtLocation(point.x, point.y);
//                if(dragTabIndex < 0 || !isEnabledAt(dragTabIndex))
//                    return;
//                initGlassPane(draggestureevent.getComponent(), draggestureevent.getDragOrigin());
//                try
//                {
//                    draggestureevent.startDrag(DragSource.DefaultMoveDrop, t, dsl);
//                }
//                catch(InvalidDnDOperationException invaliddndoperationexception)
//                {
//                    invaliddndoperationexception.printStackTrace();
//                }
//            }
//
//            final Transferable val$t;
//            final DragSourceListener val$dsl;
//            final ExtendedOpenDnDTabbedPane this$0;
//
//            {
//                this$0 = ExtendedOpenDnDTabbedPane.this;
//                t = transferable;
//                dsl = dragsourcelistener;
//                super();
//            }
//        };
//        new DropTarget(glassPane, 3, new CDropTargetListener(), true);
//        (new DragSource()).createDefaultDragGestureRecognizer(this, 3, draggesturelistener);
    }

    public void setPaintGhost(boolean flag)
    {
        hasGhost = flag;
    }

    public boolean hasGhost()
    {
        return hasGhost;
    }

    public void setPaintScrollArea(boolean flag)
    {
        isPaintScrollArea = flag;
    }

    public boolean isPaintScrollArea()
    {
        return isPaintScrollArea;
    }

    private int getTargetTabIndex(Point point)
    {
        Point point1 = SwingUtilities.convertPoint(glassPane, point, this);
        boolean flag = getTabPlacement() == 1 || getTabPlacement() == 3;
        for(int i = 0; i < getTabCount(); i++)
        {
            Rectangle rectangle1 = getBoundsAt(i);
            if(flag)
                rectangle1.setRect(rectangle1.x - rectangle1.width / 2, rectangle1.y, rectangle1.width, rectangle1.height);
            else
                rectangle1.setRect(rectangle1.x, rectangle1.y - rectangle1.height / 2, rectangle1.width, rectangle1.height);
            if(rectangle1.contains(point1))
                return i;
        }

        Rectangle rectangle = getBoundsAt(getTabCount() - 1);
        if(flag)
            rectangle.setRect(rectangle.x + rectangle.width / 2, rectangle.y, rectangle.width, rectangle.height);
        else
            rectangle.setRect(rectangle.x, rectangle.y + rectangle.height / 2, rectangle.width, rectangle.height);
        return rectangle.contains(point1) ? getTabCount() : -1;
    }

    private void convertTab(int i, int j)
    {
        if(j < 0 || i == j)
            return;
        Component component = getComponentAt(i);
        Component component1 = getTabComponentAt(i);
        String s = getTitleAt(i);
        Icon icon = getIconAt(i);
        String s1 = getToolTipTextAt(i);
        boolean flag = isEnabledAt(i);
        int k = i <= j ? j - 1 : j;
        remove(i);
        insertTab(s, icon, component, s1, k);
        setEnabledAt(k, flag);
        if(flag)
            setSelectedIndex(k);
        setTabComponentAt(k, component1);
    }

    private void initTargetLeftRightLine(int i)
    {
        if(i < 0 || dragTabIndex == i || i - dragTabIndex == 1)
            lineRect.setRect(0.0D, 0.0D, 0.0D, 0.0D);
        else
        if(i == 0)
        {
            Rectangle rectangle = SwingUtilities.convertRectangle(this, getBoundsAt(0), glassPane);
            lineRect.setRect(rectangle.x - 1, rectangle.y, 3D, rectangle.height);
        } else
        {
            Rectangle rectangle1 = SwingUtilities.convertRectangle(this, getBoundsAt(i - 1), glassPane);
            lineRect.setRect((rectangle1.x + rectangle1.width) - 1, rectangle1.y, 3D, rectangle1.height);
        }
    }

    private void initTargetTopBottomLine(int i)
    {
        if(i < 0 || dragTabIndex == i || i - dragTabIndex == 1)
            lineRect.setRect(0.0D, 0.0D, 0.0D, 0.0D);
        else
        if(i == 0)
        {
            Rectangle rectangle = SwingUtilities.convertRectangle(this, getBoundsAt(0), glassPane);
            lineRect.setRect(rectangle.x, rectangle.y - 1, rectangle.width, 3D);
        } else
        {
            Rectangle rectangle1 = SwingUtilities.convertRectangle(this, getBoundsAt(i - 1), glassPane);
            lineRect.setRect(rectangle1.x, (rectangle1.y + rectangle1.height) - 1, rectangle1.width, 3D);
        }
    }

    private void initGlassPane(Component component, Point point)
    {
        getRootPane().setGlassPane(glassPane);
        if(hasGhost())
        {
            Rectangle rectangle = getBoundsAt(dragTabIndex);
            BufferedImage bufferedimage = new BufferedImage(component.getWidth(), component.getHeight(), 2);
            Graphics g = bufferedimage.getGraphics();
            component.paint(g);
            rectangle.x = rectangle.x >= 0 ? rectangle.x : 0;
            rectangle.y = rectangle.y >= 0 ? rectangle.y : 0;
            bufferedimage = bufferedimage.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            glassPane.setImage(bufferedimage);
        }
        Point point1 = SwingUtilities.convertPoint(component, point, glassPane);
        glassPane.setPoint(point1);
        glassPane.setVisible(true);
    }

    private Rectangle getTabAreaBounds()
    {
        Rectangle rectangle = getBounds();
        Component component = getSelectedComponent();
        for(int i = 0; component == null && i < getTabCount(); component = getComponentAt(i++));
        Rectangle rectangle1 = component != null ? component.getBounds() : new Rectangle();
        int j = getTabPlacement();
        if(j == 1)
            rectangle.height = rectangle.height - rectangle1.height;
        else
        if(j == 3)
        {
            rectangle.y = rectangle.y + rectangle1.y + rectangle1.height;
            rectangle.height = rectangle.height - rectangle1.height;
        } else
        if(j == 2)
            rectangle.width = rectangle.width - rectangle1.width;
        else
        if(j == 4)
        {
            rectangle.x = rectangle.x + rectangle1.x + rectangle1.width;
            rectangle.width = rectangle.width - rectangle1.width;
        }
        rectangle.grow(2, 2);
        return rectangle;
    }

    private static final int LINEWIDTH = 3;
    private static final String NAME = "test";
    private final GhostGlassPane glassPane = new GhostGlassPane();
    private final Rectangle lineRect = new Rectangle();
    private final Color lineColor = new Color(0, 100, 255);
    private int dragTabIndex;
    private static Rectangle rBackward = new Rectangle();
    private static Rectangle rForward = new Rectangle();
    private static int rwh = 20;
    private static int buttonsize = 30;
    private boolean hasGhost;
    private boolean isPaintScrollArea;















}
