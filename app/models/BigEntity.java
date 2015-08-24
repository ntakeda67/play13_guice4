package models;



import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Query;
import play.db.jpa.JPA;
import play.db.jpa.Model;


@Entity
public class BigEntity extends Model {
    public String name;
    
    public Integer value01;
    public Integer value02;
    public Integer value03;
    public Integer value04;
    public Integer value05;

    public String str01;
    public String str02;
    public String str03;
    public String str04;
    public String str05;


    public static void insertTestData(
				       String name,
				       Integer value01,
				       Integer value02,
				       Integer value03,
				       Integer value04,
				       Integer value05,
				       String str01,
				       String str02,
				       String str03,
				       String str04,
				       String str05
				       ) {
	BigEntity b = new BigEntity();
	b.name = name;
	b.value01 = value01;
	b.value02 = value02;
	b.value03 = value03;
	b.value04 = value04;
	b.value05 = value05;
	b.str01 = str01;
	b.str02 = str02;
	b.str03 = str03;
	b.str04 = str04;
	b.str05 = str05;
	
	b.save();
    }

    public static List<BigEntity> findByValue01(Integer value01){
	return JPA.em().createNativeQuery("select * from BigEntity where value01 = ?1", BigEntity.class).setParameter(1, value01).getResultList();
    }

    public static List<BigEntity> findByExample(BigEntity example){
	String select = "select * from BigEntity ";
	List<String> whereElemList = new ArrayList<>();
	List<Object> paramList = new ArrayList<>();
	
	
	if(example.value01 != null){ whereElemList.add("value01 = ?1");}
	if(example.value02 != null){ whereElemList.add("value02 = ?2");}
	if(example.value03 != null){ whereElemList.add("value03 = ?3");}
	if(example.value04 != null){ whereElemList.add("value04 = ?4");}
	if(example.value05 != null){ whereElemList.add("value05 = ?5");}
	
	if(example.str01 != null){ whereElemList.add("str01 = ?11");}
	if(example.str02 != null){ whereElemList.add("str02 = ?12");}
	if(example.str03 != null){ whereElemList.add("str03 = ?13");}
	if(example.str04 != null){ whereElemList.add("str04 = ?14");}
	if(example.str05 != null){ whereElemList.add("str05 = ?15");}

	String whereClause = whereElemList.size() < 1 ? "" : " where " + String.join(" and ", whereElemList);

	Query query = JPA.em().createNativeQuery(select + whereClause, BigEntity.class);
	if(example.value01 != null){ query.setParameter(1, example.value01); }
	if(example.value02 != null){ query.setParameter(2, example.value02); }
	if(example.value03 != null){ query.setParameter(3, example.value03); }
	if(example.value04 != null){ query.setParameter(4, example.value04); }
	if(example.value05 != null){ query.setParameter(5, example.value05); }
	
	if(example.str01 != null){ query.setParameter(11, example.str01);}
	if(example.str02 != null){ query.setParameter(12, example.str02);}
	if(example.str03 != null){ query.setParameter(13, example.str03);}
	if(example.str04 != null){ query.setParameter(14, example.str04);}
	if(example.str05 != null){ query.setParameter(15, example.str05);}

	return query.getResultList();
    }

    public void cleansing(){
	if("".equals(this.value01)){this.value01 = null;}
	if("".equals(this.value02)){this.value02 = null;}
	if("".equals(this.value03)){this.value03 = null;}
	if("".equals(this.value04)){this.value04 = null;}
	if("".equals(this.value05)){this.value05 = null;}
	if("".equals(this.str01)){this.str01 = null;}
	if("".equals(this.str02)){this.str02 = null;}
	if("".equals(this.str03)){this.str03 = null;}
	if("".equals(this.str04)){this.str04 = null;}
	if("".equals(this.str05)){this.str05 = null;}
    }

    public static BigEntity clone(BigEntity e){
	BigEntity c = new BigEntity();
	c.value01 = e.value01 == null ? null:new Integer(e.value01);
	c.value02 = e.value02 == null ? null:new Integer(e.value02);
	c.value03 = e.value03 == null ? null:new Integer(e.value03);
	c.value04 = e.value04 == null ? null:new Integer(e.value04);
	c.value05 = e.value05 == null ? null:new Integer(e.value05);

	c.str01 = e.str01 == null ? null:new String(e.str01);
	c.str02 = e.str02 == null ? null:new String(e.str02);
	c.str03 = e.str03 == null ? null:new String(e.str03);
	c.str04 = e.str04 == null ? null:new String(e.str04);
	c.str05 = e.str05 == null ? null:new String(e.str05);
	return c;
    }
}
