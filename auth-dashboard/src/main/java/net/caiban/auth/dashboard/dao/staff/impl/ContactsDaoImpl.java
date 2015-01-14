/**
 * 
 */
package net.caiban.auth.dashboard.dao.staff.impl;

import net.caiban.auth.dashboard.dao.staff.ContactsDao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

/**
 * @author root
 *
 */
@Component("contactsDao")
public class ContactsDaoImpl extends SqlMapClientDaoSupport implements ContactsDao {

}
