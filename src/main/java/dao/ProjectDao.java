package dao;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.Article;
import models.ArticleDto;
import models.ArticlesDto;
import models.ProjectDto;
import models.Project;
import models.ProjectDto;
import models.ProjectsDto;
import models.User;
import models.UserDto;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import controllers.LoginLogoutController;

public class ProjectDao {
	@Inject UserDao userDao;
    @Inject
    Provider<EntityManager> entitiyManagerProvider;
    
    LoginLogoutController newu=new LoginLogoutController();
    

    static int x = 0;
    @UnitOfWork
    public ProjectsDto getAllProjects() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Project> q = entityManager.createQuery("SELECT x FROM Project x", Project.class);
        List<Project> projects = q.getResultList();        

        ProjectsDto projectsDto = new ProjectsDto();
        projectsDto.projects = projects;
        
        return projectsDto;
        
    }
    
    @Transactional
    public boolean postProject(String username, ProjectDto projectDto) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }
        
        Project project = new Project(user, projectDto.project_title, projectDto.project_description);
        entityManager.persist(project);
        
        return true;
        
    }
    
    @Inject
    Provider<EntityManager> entityManagerProvider;
    
    @Transactional
    public boolean newsignup(String username, UserDto userDto) {
        
    	
            EntityManager entityManager = entityManagerProvider.get();
        	System.out.println("###########################");
        	System.out.println("Value of X: "+x);
        	System.out.println("###########################");
            if(x==0){
            	User u = new User(userDto.username,userDto.password, userDto.fullname);
            	entityManager.persist(u);
            	return true;
            }else {
            	return false;
            }
           
    }

    
    @UnitOfWork
    public List<Project> getFirstProjectForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Project> q = entityManager.createQuery("SELECT x FROM Project x WHERE x.id IN (SELECT c.Project_id FROM Project_authorids c WHERE c.authorIds =:idd) ", Project.class);
        List<Project> project = q.setParameter("idd",newu.getcuser()).setMaxResults(2).getResultList();      
        
        return project;
        
        
    }
    
    @UnitOfWork
    public List<Project> getOlderProjectsForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Project> q = entityManager.createQuery("SELECT x FROM Project x WHERE x.id IN (SELECT c.Project_id FROM Project_authorids c WHERE c.authorIds =:idd) ORDER BY x.id DESC ", Project.class);
        List<Project> projects = q.setParameter("idd",newu.getcuser()).setFirstResult(0).setMaxResults(100).getResultList();            
            
        
        return projects;
        
        
    }
    @UnitOfWork
    public Project getProject(Long id) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Project> q = entityManager.createQuery("SELECT x FROM Project x WHERE x.id = :idParam", Project.class);
        Project project = q.setParameter("idParam", id).getSingleResult();        
        
        return project;
        
        
    }
     
    @UnitOfWork
    public void deleteProject(Long id) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Project> q = entityManager.createQuery("SELECT x FROM Project x WHERE x.id = :idParam", Project.class);
        Project project = q.setParameter("idParam", id).getSingleResult();
        
        entityManager.getTransaction().begin();
        entityManager.remove(project);
        entityManager.getTransaction().commit();
        
        
    }
    
    @UnitOfWork
    public User currentuser(String username) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE x.username = :nameParam", User.class);
        User user = q.setParameter("nameParam", username).getSingleResult();        
        
        return user;

    }
    
    // UPDATE

    @Transactional
    public boolean updateProject(Long id ,String username, ProjectDto projectDto) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }
        Query query = entityManager.createQuery("UPDATE Project  x SET x.project_title='"+ projectDto.project_title + "'  WHERE x.id ='" + id +"'");
      query.executeUpdate();
      Query query1 = entityManager.createQuery("UPDATE Project  x SET x.project_description='"+ projectDto.project_description + "'  WHERE x.id ='" + id +"'");
      query1.executeUpdate();
      
        
        
        return true;
        
    }
    
    @UnitOfWork
    public boolean isalreadyUser(String username, String fullname,String password) {
        
        if (username != null && fullname != null) {
            
            EntityManager entityManager = entityManagerProvider.get();
            
            TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
            User user = UserDao.getSingleResult(q.setParameter("usernameParam", username));

            if (user != null) {
                
                if (user.username.equals(username) && user.fullname.equals(fullname) || user.username.equals(username) || user.fullname.equals(fullname)) {
                	x = 1;
                    return true;
                }
                
            }
            

        }
        
        return false;
 
    }
 
}
    
    

