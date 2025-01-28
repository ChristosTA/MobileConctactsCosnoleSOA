package gr.aueb.cf.mobilecontacts.service;

import gr.aueb.cf.mobilecontacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExistException;
import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.List;

public class MobileContactServiceImpl implements IMobileContactService {

    private final IMobileContactDAO dao;

    public MobileContactServiceImpl(IMobileContactDAO dao) {
        this.dao = dao;
    }

    @Override
    public MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExistException {
        MobileContact mobileContact;

        try {
            if (dao.phoneNumberExists(dto.getPhoneNumber())){
                throw new PhoneNumberAlreadyExistException("Contact with phone number" + dto.getPhoneNumber() + ".Already exists");
            }
            mobileContact = mapInsertDTOToContact(dto);

            System.err.printf("MobileContactServiceImpl Logger: %s was inserted\n",mobileContact);
            return dao.insert(mobileContact);
        }catch (PhoneNumberAlreadyExistException e){
            System.err.printf("MobileContactServiceImpl Logger: contact with phone number: %s already exists\n",dto.getPhoneNumber());
            throw e;
        }
    }

    @Override
    public MobileContact updateMobileContact(MobileContactUpdateDTO dto)
            throws PhoneNumberAlreadyExistException, ContactNotFoundException {
        MobileContact mobileContact;
        MobileContact newContact;
        try {
            if (!dao.userIdExists(dto.getId())) {
                throw new ContactNotFoundException("Contact with Id: " + dto.getId() + "not found for update.");
            }

            mobileContact = dao.getById(dto.getId());
            boolean isPhoneNumberOurOwn = mobileContact.getPhoneNumber().equals(dto.getPhoneNumber());
            boolean isPhoneNumberExists = dao.phoneNumberExists(dto.getPhoneNumber());

            if (isPhoneNumberExists && !isPhoneNumberOurOwn) {
                throw new PhoneNumberAlreadyExistException("Contact with phone number:" + dto.getPhoneNumber() +
                        "already exists and cannot be updated");
            }
            newContact = mapUpdateDTOToContact(dto);
            System.err.printf("MobileContactServiceImpl Logger: %s was updated with new info: %s\n",mobileContact,newContact);
            return dao.update(dto.getId(), newContact);
        } catch (ContactNotFoundException | PhoneNumberAlreadyExistException e){
            System.err.println("MobileContactServiceImpl Logger:"+ e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteContactById(Long id) throws ContactNotFoundException {
        try {
            if (!dao.userIdExists(id)) {
                throw new ContactNotFoundException("Contact with id: " + id + " not found for delete.");
            }
            System.err.println("MobileContactServiceImpl Logger: contact with id: " + id + "was deleted");
            dao.deleteById(id);
        } catch (ContactNotFoundException e){
            System.err.println("MobileContactServiceImpl Logger:"+ e.getMessage());
            throw e;
        }
    }

    @Override
    public MobileContact getContactById(Long id) throws ContactNotFoundException {
        MobileContact mobileContact;
        try {
            mobileContact = dao.getById(id);
            if (mobileContact == null){
                throw new ContactNotFoundException("Contact with id: " + id + " not found");
            }
            return mobileContact;
        }catch (ContactNotFoundException e){
            System.err.println("MobileContactServiceImpl Logger: Contact with id :" + id + "was not found");
            throw e;
        }
    }

    @Override
    public List<MobileContact> getAllContacts() {
       return dao.getAll();
    }

    @Override
    public MobileContact getContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException {
        MobileContact mobileContact;


        try {
            mobileContact = dao.getByPhoneNumber(phoneNumber);
            if (mobileContact == null){
                throw new ContactNotFoundException("Contact with phone number: " + phoneNumber + " not found");
            }
            return mobileContact;
        }catch (ContactNotFoundException e){
            System.err.println("MobileContactServiceImpl Logger: Contact with phone number :" + phoneNumber + "was not found");
            throw e;
        }
    }

    @Override
    public void deleteContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException {
        try {
            if (!dao.phoneNumberExists(phoneNumber)) {
                throw new ContactNotFoundException("Contact with phone number: " + phoneNumber + " not found for delete.");
            }
            System.err.println("MobileContactServiceImpl Logger: contact with phone number: " + phoneNumber + "was deleted");
            dao.deleteByPhoneNumber(phoneNumber);
        } catch (ContactNotFoundException e){
            System.err.println("MobileContactServiceImpl Logger:"+ e.getMessage());
            throw e;
        }

    }

    private MobileContact mapInsertDTOToContact(MobileContactInsertDTO dto) {
        return  new MobileContact(null,dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    private MobileContact mapUpdateDTOToContact(MobileContactUpdateDTO dto) {
        return  new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }
}
