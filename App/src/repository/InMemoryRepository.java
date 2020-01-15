package repository;

import org.springframework.data.domain.Pageable;
import validators.Entity;
import validators.Nota;
import validators.ValidationException;
import validators.Validator;

import java.util.*;
import java.util.function.Predicate;

public class InMemoryRepository<ID, E extends Entity<ID>> implements CrudRepository<ID,E>{
    private Map<ID,E> entities;
    private Validator<E> validator;


    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Iterable<E> findPage(Pageable pageable) {
//        System.out.println(pageable);
        TreeMap<ID,E> myNewMap = entities.entrySet().stream()
                .filter(x->  position(x.getValue()) > (pageable.getPageNumber()-1)*pageable.getPageSize() )
//                .filter(x->  (long)x.getValue().getId() > (pageable.getPageNumber()-1)*pageable.getPageSize() )
                .limit(pageable.getPageSize())
//                .filter(x->  (long)x.getValue().getId() > (pageable.getPageNumber()-1)*pageable.getPageSize() && (long)x.getValue().getId() <= (pageable.getPageNumber())*pageable.getPageSize())
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
//        System.out.println(myNewMap);
        return myNewMap.values();
    }
    public long position(E valoare){
        int nr = 0;
        for (E entity:
             entities.values()) {
            nr++;
            if(entity.equals(valoare))
                break;
        }
        return nr;
    }

    /**
     * Functie de findOne care returneaza entitatea cu un anumit id
     * input- id
     * preconditii- id ID
     * output- entity
     * postconditii- entity E
     */
    @Override
    public E findOne(ID id) {
        return entities.get(id);
    }



    /**
     * Functie de findAll care returneaza toate entitatile salvate in repository
     * input-
     * preconditii-
     * output- entities.values
     * postconditii- entities.values - Iterable<E>
     */
//    @Override
//    public Iterable<E> findAll(Pageable pageable) {
//        return null;
//    }
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    /**
     * Functie de save care salveaza entitatea in repository
     * input- entity
     * preconditii- entity E
     * output- entity/ null
     * postconditii- entity daca este invalid sau null daca a fost adaugat
     */
    public E save(E entity) throws ValidationException {
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null");
        try {
            validator.validate(entity);
            if(entities.containsKey(entity.getId())) {
                //throw new ValidationException("entity not valid");
                return entity;
            }
            entities.put(entity.getId(), entity);
        }catch (ValidationException ex){
            throw new ValidationException("entity not valid");
        }

        return null;
    }
    /**
     * Functie de delete care sterge entitatea din repository
     * input- id
     * preconditii- id ID
     * output- entity
     * postconditii- returneaza entitatea stearsa
     */
    @Override
    public E delete(ID id) {
        if(id== null)
            throw  new IllegalArgumentException("id is null");
        return entities.remove(id);
    }
    /**
     * Functie de update care modifica entitatea in repository
     * input- entity
     * preconditii- entity E
     * output- entity/ null
     * postconditii- entity daca este invalid sau null daca a fost modificata
     */
    @Override
    public E update(E entity)  {
    	if(entity.getId()== null)
            throw  new IllegalArgumentException("id is null");
        if(entities.containsKey(entity.getId())) {
            try {
                validator.validate(entity);
            	delete(entity.getId());
                entities.put(entity.getId(), entity);
                return null;
            } catch (ValidationException e) {
            }
        }
        return entity;
    }
    /**
     * Functie de FindId care returneaza urmatorul id disponibil
     * input-
     * preconditii-
     * output- maxim
     * postconditii- maxim- id-ul urmator disponibil
     */
    public long findID() {
        long maxim = 0;
        for(E ent: entities.values()){
            if((long)ent.getId()>maxim)
                maxim=(long)ent.getId();
        }
        return maxim+1;
    }
}


