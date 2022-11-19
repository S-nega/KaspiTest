package kz.narxoz.demokaspi.services.servisecImpl;

import kz.narxoz.demokaspi.entity.Iban;
import kz.narxoz.demokaspi.repository.IbanRepository;
import kz.narxoz.demokaspi.services.IbanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IbanServiceImpl implements IbanService {
    @Autowired
    private IbanRepository ibanRepository;

    @Override
    public List<Iban> findAllIbans() {
        List<Iban> ibans = ibanRepository.findAll();
        return ibans;
    }

    @Override
    public void saveIban(Iban iban) {
        ibanRepository.save(iban);
    }

    @Override
    public Iban findOneById(int id) {
        return ibanRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteIban(int id) {
        ibanRepository.deleteById(id);
    }
}
