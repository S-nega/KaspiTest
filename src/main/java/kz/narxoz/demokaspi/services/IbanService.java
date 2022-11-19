package kz.narxoz.demokaspi.services;

import kz.narxoz.demokaspi.entity.Iban;

import java.util.List;

public interface IbanService {
    public List<Iban> findAllIbans();
    void saveIban(Iban iban);
    Iban findOneById(int id);
    void deleteIban(int id);

//    int findOneByUserId(int id);
}
