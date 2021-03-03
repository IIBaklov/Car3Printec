package com.ivo.cars.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ivo.cars.config.MessagingConfig;
import com.ivo.cars.exc.CarNotFoundException;
import com.ivo.cars.model.Car;
import com.ivo.cars.model.Mark;
import com.ivo.cars.model.Model;
import com.ivo.cars.model.OwnerOfCar;
import com.ivo.cars.repo.CarRepository;
import com.ivo.cars.repo.MarkRepository;
import com.ivo.cars.repo.ModelRepository;
import com.ivo.cars.repo.OwnerOfCarRepository;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CarController {

	final String NAME_SORT="orderBy";
	final String TYPE_DESC="direction";
	final String NAME_PAGE="page";
	final String NAME_PSIZE="pageSize";
	@Autowired
	CarRepository repository;
	
	@Autowired
	MarkRepository markRepository;
	
	@Autowired
	ModelRepository modelRepository;
	
	@Autowired
	OwnerOfCarRepository ownerRepository;
		
	@Autowired
	RabbitTemplate template;
	
	@Autowired
	RabbitTemplate templateConsume;
	
	@Autowired
    private RestTemplate tmpl;
	
	//Get all Cars
    @GetMapping("/cars")
    public ResponseEntity<List<Car>>  all(@RequestParam Map<String,String> allParams) {
        System.out.println("Parameters are " + allParams.entrySet());
        
        List<Car> cars=null;
        int page=0;
        int size =3;
        String nameSort=null;
        String asc_desc=null;
        int flagPagination=0;
        String myQuery=" ";
        boolean flagNoEnter=true;
        for (String key : allParams.keySet()) {
        	flagNoEnter=false;
        	switch (key) {
        		case NAME_PAGE:{
        			myQuery+=" Limit "+allParams.get(key);
        			page= Integer.parseInt(allParams.get(key));
        			flagPagination++;
        		}
        		break;
        		case NAME_PSIZE:{
        			myQuery+=" OFFSET "+allParams.get(key);
        			size= Integer.parseInt(allParams.get(key));
        			flagPagination++;
        		}
        		break;
        		case NAME_SORT:{
        			myQuery+=" ORDER BY "+allParams.get(key); 
        			nameSort = allParams.get(key);
        		}
        		break;
        		case TYPE_DESC:{
        			myQuery+=" "+allParams.get(key);
        		}
        		
        		
       		break;
        		default:  cars = repository.findAll();
        	}
        	
        	if (flagPagination==2) {
        		Pageable paging = PageRequest.of(page, size);
        		if (nameSort != null) {
        			if (asc_desc==null) {
        				paging = PageRequest.of(page, size, Sort.by(nameSort).ascending());
        			}
        			else {
        				paging = PageRequest.of(page, size, Sort.by(nameSort).descending());
        			}
        			
        		}
        		Page<Car> carPage = repository.findAll(paging);
        		cars= carPage.getContent();
        		
        	}
			
		}

        if (flagNoEnter) {
        	cars = repository.findAll();
        }
        
        
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }
    
  //Get all models
    @GetMapping("/models")
    public ResponseEntity<List<Model>>  allModels() {
    	List<Model> models= modelRepository.findAll();
        
        return ResponseEntity.status(HttpStatus.OK).body(models);
    }
  //Get all models
    @GetMapping("/owners")
    public ResponseEntity<List<OwnerOfCar>>  allOwners() {
    	List<OwnerOfCar> owners= ownerRepository.findAll();
        
        return ResponseEntity.status(HttpStatus.OK).body(owners);
    }
    
  //Get all models
    @GetMapping("/models/{mark}")
    public ResponseEntity<List<Model>>  allModelsFroMarks(@PathVariable int mark) {
    	List<Model> models= modelRepository.findModelByMark(mark);
        
        return ResponseEntity.status(HttpStatus.OK).body(models);
    }
    
    
  //Get all marks
    @GetMapping("/marks")
    public ResponseEntity<List<Mark>>  allMarks() {
    	List<Mark> marks= markRepository.findAll();
        
        return ResponseEntity.status(HttpStatus.OK).body(marks);
    }
    

    
  //Create Car
    @PostMapping("/addCar")
    public ResponseEntity<?> newCar(@RequestBody Car newCar) throws URISyntaxException {
    
    	 try {
    		 ConnectionFactory cf = new ConnectionFactory();
    		 Connection conn = cf.newConnection();

    		 Channel ch = conn.createChannel();;
    		 ch.queueDeclare(MessagingConfig.QUEUE,false,false,false,null);
    		 template.convertAndSend(MessagingConfig.QUEUE,newCar);
    		 System.out.println("Message was sent to rabbit!!Queue is:id:"+newCar.getRegNum()+"->"+ LocalDateTime.now());
    		 try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
       return ResponseEntity.status(HttpStatus.OK).body(newCar);

    }
    
        
    @DeleteMapping("/delCar/{regNum}")
    public ResponseEntity<?> deleteCar(@PathVariable String regNum) {
    	boolean flag=true;
   	 Car car1 = repository.findByRegNum(regNum);
   	 if (car1 != null) {
   		 repository.delete(car1);
   	 		return ResponseEntity.status(HttpStatus.OK).body(car1);
   	 }
   	 else {
   		 	
   	 		return ResponseEntity.notFound().build();
   	 }
        
    }
    
      
  //Get one Car
    @GetMapping("/car/{id}")
    public ResponseEntity<Car> one(@PathVariable String id) {

        Car car = repository.findByRegNum(id);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }
    
  //Update Car
    @PutMapping("/updCar")
    public ResponseEntity<?> updateCar(@RequestBody Car car) throws URISyntaxException {
    	boolean flag=true;
    	 Car car1 = repository.findByRegNum(car.getRegNum());
    	 if (car1 != null) {
    		 repository.save(car);
    	 		return ResponseEntity.status(HttpStatus.OK).body(car);
    	 }
    	 else {
    		 	
    	 		return ResponseEntity.notFound().build();
    	 }
        

    }
}
