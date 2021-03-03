package com.ivo.cars.consumer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ivo.cars.config.MessagingConfig;
import com.ivo.cars.model.Car;
import com.ivo.cars.model.Mark;
import com.ivo.cars.repo.CarRepository;
import com.ivo.cars.repo.MarkRepository;

@Component
@RabbitListener(queues=MessagingConfig.QUEUE )
public class ConsumerCars {
	@Autowired
	CarRepository repository;
	@Autowired
	MarkRepository markRepository;
	
	@RabbitHandler
	public void consumeMessageFromQueue(Car newCar) {
	System.out.println("Message Receive form queue is:id:"+newCar.getRegNum()+"->in time:"+LocalDateTime.now());
	
  	 repository.save(newCar);
	}
}
