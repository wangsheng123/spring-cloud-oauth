package com.ws.icloud.user;

public class Test {

    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 类似归并排序中的合并过程
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                cur = cur.next;
                l1 = l1.next;
            } else {
                cur.next = l2;
                cur = cur.next;
                l2 = l2.next;
            }
        }
        // 任一为空，直接连接另一条链表
        if (l1 == null) {
            cur.next = l2;
        } else {
            cur.next = l1;
        }
        return dummyHead.next;
    }

   /* public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode curr = l1;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = l2;

        return sort(l1);
    }*/

    public ListNode sort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = head;
        //当前待排序的节点
        ListNode cur = head.next;
        //辅助节点，永远指向头结点
        ListNode aux = new ListNode(0);
        aux.next = head;
        while (cur != null) {
            if (cur.val < pre.val) {
                //先把cur节点从当前链表中删除，然后再把cur节点插入到合适位置
                pre.next = cur.next;
                //从前往后找到node2.val>cur.val,然后把cur节点插入到node1和node2之间
                ListNode node1 = aux;
                ListNode node2 = aux.next;
                while (cur.val > node2.val) {
                    node1 = node2;
                    node2 = node2.next;
                }
                //把cur节点插入到node1和node2之间
                node1.next = cur;
                cur.next = node2;
                //cur节点向后移动一位
                cur = pre.next;

            } else {
                //向后移动
                pre = cur;
                cur = cur.next;
            }
        }
        return aux.next;
    }

    @org.junit.Test
    public void test() {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(4);
        ListNode listNode2 = new ListNode(1);
        listNode2.next = new ListNode(3);
        listNode2.next.next = new ListNode(4);
        ListNode all = mergeTwoLists(listNode, listNode2);
        System.out.println(all);

    }

}
