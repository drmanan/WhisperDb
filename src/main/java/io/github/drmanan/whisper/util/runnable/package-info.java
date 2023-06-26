/*
 * Copyright (c) Manan Sharma 2023.
 */

/**
 *
 * The Package contains code enabling listeners on threads.
 *
 * <p>
 * Determining when a Thread has finished can get ugly real quick.
 * Passing references to the Thread of concrete objects wishing to be
 * notified upon completion can lock the code into a particular design,
 * making it hard to pull it apart at a later date and difficult to adapt,
 * rewrite, reuse, and redesign later on.
 *
 * <p>
 * The Observer Pattern can be used to design a method to decouple the
 * thread from the client, making the design reusable and more amenable
 * to change. This pattern allows a client to listen for particular events
 * from another class, in our case to listen for when the thread is
 * completed.
 *
 * @author Manan Sharma
 * @version 1
 * @since Monday, 26 of June, 2023; 23:39:09
 */

package io.github.drmanan.whisper.util.runnable;