package com.github.alexeylapin.ocp.collections;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class QueueTest {

    // add
    // offer

    // element
    // peek

    // remove
    // poll

    @Test
    void queue() {
//        Queue<Integer> queue = new ArrayDeque<>();

        Queue<Integer> queue = new LinkedBlockingQueue<>(2);

        boolean add = queue.add(1);
        assertThat(add).isTrue();
        boolean offer = queue.offer(2);
        assertThat(offer).isTrue();

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> queue.add(3));
        assertThat(queue.offer(3)).isFalse();

        Integer element = queue.element();
        assertThat(element).isEqualTo(1);
        Integer peek = queue.peek();
        assertThat(peek).isEqualTo(1);

        Integer remove = queue.remove();
        assertThat(remove).isEqualTo(1);
        Integer poll = queue.poll();
        assertThat(poll).isEqualTo(2);

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> queue.element());
        assertThat(queue.peek()).isNull();

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> queue.remove());
        assertThat(queue.poll()).isNull();
    }

    // add -> addLast
    // addFirst
    // addLast

    // offer -> offerLat
    // offerFirst
    // offerLast

    // element -> getFirst
    // getFirst
    // getLast

    // peek -> peekFirst
    // peekFirst
    // peekLast

    // remove -> removeFirst
    // removeFirst
    // removeLast

    // push -> addFirst
    // pop -> removeFirst
}
