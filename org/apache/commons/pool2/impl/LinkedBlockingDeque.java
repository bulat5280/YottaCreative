package org.apache.commons.pool2.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

class LinkedBlockingDeque<E> extends AbstractQueue<E> implements Deque<E>, Serializable {
   private static final long serialVersionUID = -387911632671998426L;
   private transient LinkedBlockingDeque.Node<E> first;
   private transient LinkedBlockingDeque.Node<E> last;
   private transient int count;
   private final int capacity;
   private final InterruptibleReentrantLock lock;
   private final Condition notEmpty;
   private final Condition notFull;

   public LinkedBlockingDeque() {
      this(Integer.MAX_VALUE);
   }

   public LinkedBlockingDeque(boolean fairness) {
      this(Integer.MAX_VALUE, fairness);
   }

   public LinkedBlockingDeque(int capacity) {
      this(capacity, false);
   }

   public LinkedBlockingDeque(int capacity, boolean fairness) {
      if (capacity <= 0) {
         throw new IllegalArgumentException();
      } else {
         this.capacity = capacity;
         this.lock = new InterruptibleReentrantLock(fairness);
         this.notEmpty = this.lock.newCondition();
         this.notFull = this.lock.newCondition();
      }
   }

   public LinkedBlockingDeque(Collection<? extends E> c) {
      this(Integer.MAX_VALUE);
      this.lock.lock();

      try {
         Iterator i$ = c.iterator();

         while(i$.hasNext()) {
            E e = i$.next();
            if (e == null) {
               throw new NullPointerException();
            }

            if (!this.linkLast(e)) {
               throw new IllegalStateException("Deque full");
            }
         }
      } finally {
         this.lock.unlock();
      }

   }

   private boolean linkFirst(E e) {
      if (this.count >= this.capacity) {
         return false;
      } else {
         LinkedBlockingDeque.Node<E> f = this.first;
         LinkedBlockingDeque.Node<E> x = new LinkedBlockingDeque.Node(e, (LinkedBlockingDeque.Node)null, f);
         this.first = x;
         if (this.last == null) {
            this.last = x;
         } else {
            f.prev = x;
         }

         ++this.count;
         this.notEmpty.signal();
         return true;
      }
   }

   private boolean linkLast(E e) {
      if (this.count >= this.capacity) {
         return false;
      } else {
         LinkedBlockingDeque.Node<E> l = this.last;
         LinkedBlockingDeque.Node<E> x = new LinkedBlockingDeque.Node(e, l, (LinkedBlockingDeque.Node)null);
         this.last = x;
         if (this.first == null) {
            this.first = x;
         } else {
            l.next = x;
         }

         ++this.count;
         this.notEmpty.signal();
         return true;
      }
   }

   private E unlinkFirst() {
      LinkedBlockingDeque.Node<E> f = this.first;
      if (f == null) {
         return null;
      } else {
         LinkedBlockingDeque.Node<E> n = f.next;
         E item = f.item;
         f.item = null;
         f.next = f;
         this.first = n;
         if (n == null) {
            this.last = null;
         } else {
            n.prev = null;
         }

         --this.count;
         this.notFull.signal();
         return item;
      }
   }

   private E unlinkLast() {
      LinkedBlockingDeque.Node<E> l = this.last;
      if (l == null) {
         return null;
      } else {
         LinkedBlockingDeque.Node<E> p = l.prev;
         E item = l.item;
         l.item = null;
         l.prev = l;
         this.last = p;
         if (p == null) {
            this.first = null;
         } else {
            p.next = null;
         }

         --this.count;
         this.notFull.signal();
         return item;
      }
   }

   private void unlink(LinkedBlockingDeque.Node<E> x) {
      LinkedBlockingDeque.Node<E> p = x.prev;
      LinkedBlockingDeque.Node<E> n = x.next;
      if (p == null) {
         this.unlinkFirst();
      } else if (n == null) {
         this.unlinkLast();
      } else {
         p.next = n;
         n.prev = p;
         x.item = null;
         --this.count;
         this.notFull.signal();
      }

   }

   public void addFirst(E e) {
      if (!this.offerFirst(e)) {
         throw new IllegalStateException("Deque full");
      }
   }

   public void addLast(E e) {
      if (!this.offerLast(e)) {
         throw new IllegalStateException("Deque full");
      }
   }

   public boolean offerFirst(E e) {
      if (e == null) {
         throw new NullPointerException();
      } else {
         this.lock.lock();

         boolean var2;
         try {
            var2 = this.linkFirst(e);
         } finally {
            this.lock.unlock();
         }

         return var2;
      }
   }

   public boolean offerLast(E e) {
      if (e == null) {
         throw new NullPointerException();
      } else {
         this.lock.lock();

         boolean var2;
         try {
            var2 = this.linkLast(e);
         } finally {
            this.lock.unlock();
         }

         return var2;
      }
   }

   public void putFirst(E e) throws InterruptedException {
      if (e == null) {
         throw new NullPointerException();
      } else {
         this.lock.lock();

         try {
            while(!this.linkFirst(e)) {
               this.notFull.await();
            }
         } finally {
            this.lock.unlock();
         }

      }
   }

   public void putLast(E e) throws InterruptedException {
      if (e == null) {
         throw new NullPointerException();
      } else {
         this.lock.lock();

         try {
            while(!this.linkLast(e)) {
               this.notFull.await();
            }
         } finally {
            this.lock.unlock();
         }

      }
   }

   public boolean offerFirst(E e, long timeout, TimeUnit unit) throws InterruptedException {
      if (e == null) {
         throw new NullPointerException();
      } else {
         long nanos = unit.toNanos(timeout);
         this.lock.lockInterruptibly();

         try {
            boolean var7;
            while(!this.linkFirst(e)) {
               if (nanos <= 0L) {
                  var7 = false;
                  return var7;
               }

               nanos = this.notFull.awaitNanos(nanos);
            }

            var7 = true;
            return var7;
         } finally {
            this.lock.unlock();
         }
      }
   }

   public boolean offerLast(E e, long timeout, TimeUnit unit) throws InterruptedException {
      if (e == null) {
         throw new NullPointerException();
      } else {
         long nanos = unit.toNanos(timeout);
         this.lock.lockInterruptibly();

         try {
            boolean var7;
            while(!this.linkLast(e)) {
               if (nanos <= 0L) {
                  var7 = false;
                  return var7;
               }

               nanos = this.notFull.awaitNanos(nanos);
            }

            var7 = true;
            return var7;
         } finally {
            this.lock.unlock();
         }
      }
   }

   public E removeFirst() {
      E x = this.pollFirst();
      if (x == null) {
         throw new NoSuchElementException();
      } else {
         return x;
      }
   }

   public E removeLast() {
      E x = this.pollLast();
      if (x == null) {
         throw new NoSuchElementException();
      } else {
         return x;
      }
   }

   public E pollFirst() {
      this.lock.lock();

      Object var1;
      try {
         var1 = this.unlinkFirst();
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public E pollLast() {
      this.lock.lock();

      Object var1;
      try {
         var1 = this.unlinkLast();
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public E takeFirst() throws InterruptedException {
      this.lock.lock();

      Object var2;
      try {
         Object x;
         while((x = this.unlinkFirst()) == null) {
            this.notEmpty.await();
         }

         var2 = x;
      } finally {
         this.lock.unlock();
      }

      return var2;
   }

   public E takeLast() throws InterruptedException {
      this.lock.lock();

      Object var2;
      try {
         Object x;
         while((x = this.unlinkLast()) == null) {
            this.notEmpty.await();
         }

         var2 = x;
      } finally {
         this.lock.unlock();
      }

      return var2;
   }

   public E pollFirst(long timeout, TimeUnit unit) throws InterruptedException {
      long nanos = unit.toNanos(timeout);
      this.lock.lockInterruptibly();

      Object var7;
      try {
         Object x;
         while((x = this.unlinkFirst()) == null) {
            if (nanos <= 0L) {
               var7 = null;
               return var7;
            }

            nanos = this.notEmpty.awaitNanos(nanos);
         }

         var7 = x;
      } finally {
         this.lock.unlock();
      }

      return var7;
   }

   public E pollLast(long timeout, TimeUnit unit) throws InterruptedException {
      long nanos = unit.toNanos(timeout);
      this.lock.lockInterruptibly();

      Object var7;
      try {
         Object x;
         while((x = this.unlinkLast()) == null) {
            if (nanos <= 0L) {
               var7 = null;
               return var7;
            }

            nanos = this.notEmpty.awaitNanos(nanos);
         }

         var7 = x;
      } finally {
         this.lock.unlock();
      }

      return var7;
   }

   public E getFirst() {
      E x = this.peekFirst();
      if (x == null) {
         throw new NoSuchElementException();
      } else {
         return x;
      }
   }

   public E getLast() {
      E x = this.peekLast();
      if (x == null) {
         throw new NoSuchElementException();
      } else {
         return x;
      }
   }

   public E peekFirst() {
      this.lock.lock();

      Object var1;
      try {
         var1 = this.first == null ? null : this.first.item;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public E peekLast() {
      this.lock.lock();

      Object var1;
      try {
         var1 = this.last == null ? null : this.last.item;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public boolean removeFirstOccurrence(Object o) {
      if (o == null) {
         return false;
      } else {
         this.lock.lock();

         try {
            for(LinkedBlockingDeque.Node p = this.first; p != null; p = p.next) {
               if (o.equals(p.item)) {
                  this.unlink(p);
                  boolean var3 = true;
                  return var3;
               }
            }

            boolean var7 = false;
            return var7;
         } finally {
            this.lock.unlock();
         }
      }
   }

   public boolean removeLastOccurrence(Object o) {
      if (o == null) {
         return false;
      } else {
         this.lock.lock();

         boolean var7;
         try {
            for(LinkedBlockingDeque.Node p = this.last; p != null; p = p.prev) {
               if (o.equals(p.item)) {
                  this.unlink(p);
                  boolean var3 = true;
                  return var3;
               }
            }

            var7 = false;
         } finally {
            this.lock.unlock();
         }

         return var7;
      }
   }

   public boolean add(E e) {
      this.addLast(e);
      return true;
   }

   public boolean offer(E e) {
      return this.offerLast(e);
   }

   public void put(E e) throws InterruptedException {
      this.putLast(e);
   }

   public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
      return this.offerLast(e, timeout, unit);
   }

   public E remove() {
      return this.removeFirst();
   }

   public E poll() {
      return this.pollFirst();
   }

   public E take() throws InterruptedException {
      return this.takeFirst();
   }

   public E poll(long timeout, TimeUnit unit) throws InterruptedException {
      return this.pollFirst(timeout, unit);
   }

   public E element() {
      return this.getFirst();
   }

   public E peek() {
      return this.peekFirst();
   }

   public int remainingCapacity() {
      this.lock.lock();

      int var1;
      try {
         var1 = this.capacity - this.count;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public int drainTo(Collection<? super E> c) {
      return this.drainTo(c, Integer.MAX_VALUE);
   }

   public int drainTo(Collection<? super E> c, int maxElements) {
      if (c == null) {
         throw new NullPointerException();
      } else if (c == this) {
         throw new IllegalArgumentException();
      } else {
         this.lock.lock();

         try {
            int n = Math.min(maxElements, this.count);

            int i;
            for(i = 0; i < n; ++i) {
               c.add(this.first.item);
               this.unlinkFirst();
            }

            i = n;
            return i;
         } finally {
            this.lock.unlock();
         }
      }
   }

   public void push(E e) {
      this.addFirst(e);
   }

   public E pop() {
      return this.removeFirst();
   }

   public boolean remove(Object o) {
      return this.removeFirstOccurrence(o);
   }

   public int size() {
      this.lock.lock();

      int var1;
      try {
         var1 = this.count;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public boolean contains(Object o) {
      if (o == null) {
         return false;
      } else {
         this.lock.lock();

         boolean var7;
         try {
            for(LinkedBlockingDeque.Node p = this.first; p != null; p = p.next) {
               if (o.equals(p.item)) {
                  boolean var3 = true;
                  return var3;
               }
            }

            var7 = false;
         } finally {
            this.lock.unlock();
         }

         return var7;
      }
   }

   public Object[] toArray() {
      this.lock.lock();

      try {
         Object[] a = new Object[this.count];
         int k = 0;

         for(LinkedBlockingDeque.Node p = this.first; p != null; p = p.next) {
            a[k++] = p.item;
         }

         Object[] var7 = a;
         return var7;
      } finally {
         this.lock.unlock();
      }
   }

   public <T> T[] toArray(T[] a) {
      this.lock.lock();

      Object[] var7;
      try {
         if (a.length < this.count) {
            a = (Object[])((Object[])Array.newInstance(a.getClass().getComponentType(), this.count));
         }

         int k = 0;

         for(LinkedBlockingDeque.Node p = this.first; p != null; p = p.next) {
            a[k++] = p.item;
         }

         if (a.length > k) {
            a[k] = null;
         }

         var7 = a;
      } finally {
         this.lock.unlock();
      }

      return var7;
   }

   public String toString() {
      this.lock.lock();

      String var1;
      try {
         var1 = super.toString();
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public void clear() {
      this.lock.lock();

      try {
         LinkedBlockingDeque.Node n;
         for(LinkedBlockingDeque.Node f = this.first; f != null; f = n) {
            f.item = null;
            n = f.next;
            f.prev = null;
            f.next = null;
         }

         this.first = this.last = null;
         this.count = 0;
         this.notFull.signalAll();
      } finally {
         this.lock.unlock();
      }
   }

   public Iterator<E> iterator() {
      return new LinkedBlockingDeque.Itr();
   }

   public Iterator<E> descendingIterator() {
      return new LinkedBlockingDeque.DescendingItr();
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      this.lock.lock();

      try {
         s.defaultWriteObject();

         for(LinkedBlockingDeque.Node p = this.first; p != null; p = p.next) {
            s.writeObject(p.item);
         }

         s.writeObject((Object)null);
      } finally {
         this.lock.unlock();
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.count = 0;
      this.first = null;
      this.last = null;

      while(true) {
         E item = s.readObject();
         if (item == null) {
            return;
         }

         this.add(item);
      }
   }

   public boolean hasTakeWaiters() {
      this.lock.lock();

      boolean var1;
      try {
         var1 = this.lock.hasWaiters(this.notEmpty);
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public int getTakeQueueLength() {
      this.lock.lock();

      int var1;
      try {
         var1 = this.lock.getWaitQueueLength(this.notEmpty);
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public void interuptTakeWaiters() {
      this.lock.lock();

      try {
         this.lock.interruptWaiters(this.notEmpty);
      } finally {
         this.lock.unlock();
      }

   }

   private class DescendingItr extends LinkedBlockingDeque<E>.AbstractItr {
      private DescendingItr() {
         super();
      }

      LinkedBlockingDeque.Node<E> firstNode() {
         return LinkedBlockingDeque.this.last;
      }

      LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> n) {
         return n.prev;
      }

      // $FF: synthetic method
      DescendingItr(Object x1) {
         this();
      }
   }

   private class Itr extends LinkedBlockingDeque<E>.AbstractItr {
      private Itr() {
         super();
      }

      LinkedBlockingDeque.Node<E> firstNode() {
         return LinkedBlockingDeque.this.first;
      }

      LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> n) {
         return n.next;
      }

      // $FF: synthetic method
      Itr(Object x1) {
         this();
      }
   }

   private abstract class AbstractItr implements Iterator<E> {
      LinkedBlockingDeque.Node<E> next;
      E nextItem;
      private LinkedBlockingDeque.Node<E> lastRet;

      abstract LinkedBlockingDeque.Node<E> firstNode();

      abstract LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> var1);

      AbstractItr() {
         LinkedBlockingDeque.this.lock.lock();

         try {
            this.next = this.firstNode();
            this.nextItem = this.next == null ? null : this.next.item;
         } finally {
            LinkedBlockingDeque.this.lock.unlock();
         }

      }

      private LinkedBlockingDeque.Node<E> succ(LinkedBlockingDeque.Node<E> n) {
         while(true) {
            LinkedBlockingDeque.Node<E> s = this.nextNode(n);
            if (s == null) {
               return null;
            }

            if (s.item != null) {
               return s;
            }

            if (s == n) {
               return this.firstNode();
            }

            n = s;
         }
      }

      void advance() {
         LinkedBlockingDeque.this.lock.lock();

         try {
            this.next = this.succ(this.next);
            this.nextItem = this.next == null ? null : this.next.item;
         } finally {
            LinkedBlockingDeque.this.lock.unlock();
         }

      }

      public boolean hasNext() {
         return this.next != null;
      }

      public E next() {
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            this.lastRet = this.next;
            E x = this.nextItem;
            this.advance();
            return x;
         }
      }

      public void remove() {
         LinkedBlockingDeque.Node<E> n = this.lastRet;
         if (n == null) {
            throw new IllegalStateException();
         } else {
            this.lastRet = null;
            LinkedBlockingDeque.this.lock.lock();

            try {
               if (n.item != null) {
                  LinkedBlockingDeque.this.unlink(n);
               }
            } finally {
               LinkedBlockingDeque.this.lock.unlock();
            }

         }
      }
   }

   private static final class Node<E> {
      E item;
      LinkedBlockingDeque.Node<E> prev;
      LinkedBlockingDeque.Node<E> next;

      Node(E x, LinkedBlockingDeque.Node<E> p, LinkedBlockingDeque.Node<E> n) {
         this.item = x;
         this.prev = p;
         this.next = n;
      }
   }
}
