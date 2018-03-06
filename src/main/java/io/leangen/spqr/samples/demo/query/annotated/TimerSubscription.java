package io.leangen.spqr.samples.demo.query.annotated;

import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.spqr.samples.demo.dto.Timer;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author estebes
 */

@Component
public class TimerSubscription {
	@GraphQLSubscription(name = "timer", description = "Timer subscription")
	public Publisher<Timer> timer() {
		Observable<Timer> observable = Observable
				.interval(1, TimeUnit.SECONDS)
				.flatMap(n -> Observable.create(observableEmitter -> {
					observableEmitter.onNext(new Timer(LocalDateTime.now()));
				}));

		return observable.toFlowable(BackpressureStrategy.BUFFER);
	}
}
