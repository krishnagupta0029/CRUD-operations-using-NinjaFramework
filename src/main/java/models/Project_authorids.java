package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Project_authorids {
	 @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
public long Project_id;
public long authorIds;


public Project_authorids() {}
}