package com.github.euler.graal;

import java.io.IOException;

import org.graalvm.polyglot.Context;

import com.github.euler.core.Item;
import com.github.euler.core.JobTaskToProcess;
import com.github.euler.core.TaskCommand;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.ReceiveBuilder;
import akka.actor.typed.scaladsl.Behaviors;

public abstract class GraalExecution extends AbstractBehavior<TaskCommand> {

    private Context graalContext;

    public GraalExecution(ActorContext<TaskCommand> context) {
        super(context);
        this.graalContext = buildGraalContext();
    }

    protected Context buildGraalContext() {
        return Context
                .newBuilder()
                .allowAllAccess(true)
                .build();
    }

    @Override
    public Receive<TaskCommand> createReceive() {
        ReceiveBuilder<TaskCommand> builder = newReceiveBuilder();
        builder.onMessage(JobTaskToProcess.class, this::onJobTaskToProcess);
        builder.onSignal(PostStop.class, this::onPostStop);
        return builder.build();
    }

    private Behavior<TaskCommand> onJobTaskToProcess(JobTaskToProcess msg) throws IOException {
        process(graalContext, new Item(msg));
        return Behaviors.same();
    }

    protected abstract void process(Context context, Item item);

    private Behavior<TaskCommand> onPostStop(PostStop signal) {
        finish();
        return Behaviors.same();
    }

    public void finish() {
        if (graalContext != null) {
            graalContext.close(true);
        }
    }

}
