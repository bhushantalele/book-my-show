package models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass

public class BaseModel {

    //Common Attributes among all entities

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //PrimaryKey

    private Date createdAt;
    private Date lastModifiedAt;
}
