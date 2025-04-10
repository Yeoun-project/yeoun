package yeoun.question.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
<<<<<<< HEAD:backend/src/main/java/yeoun/question/domain/CategoryEntity.java
@ToString
public class CategoryEntity {
=======
public class Category {
>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca:backend/src/main/java/yeoun/question/domain/Category.java

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

}
