package com.jcalvopinam.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcalvopinam.api.dao.IPersonDao;
import com.jcalvopinam.api.model.Person;
import com.jcalvopinam.api.service.IPersonService;

/**
 * @author Juan Calvopina M. <juan.calvopina@gmail.com>
 *
 */

@Service
public class PersonServiceImpl implements IPersonService {

//    @Autowired
    private IPersonDao personDao;

    @Transactional
    public void add(Person person) {
        personDao.add(person);
    }

    @Transactional
    public void edit(Person person) {
        personDao.edit(person);
    }

    @Transactional
    public void delete(int id) {
        personDao.delete(id);
    }

    @Transactional
    public Person getPerson(int id) {
        return personDao.getPerson(id);
    }

    @Transactional
    public List<Person> getAllPersons() {
        return personDao.getAllPersons();
    }

}
