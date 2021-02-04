package ${groupId}.common.jpahelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @param <T> T is the type for the entity to which we are creating a repository for. This entity's Identification is a Long.
 *            <p>This interface brings up all the methods from the spring data @see org.springframework.data.jpa.repository.JpaRepository
 *            and also specification executor methods in org.springframework.data.jpa.repository.JpaSpecificationExecutor</p>
 * @author: Davidche
 * @version: 1.0.0
 * @since: 1.0.0
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}