package dao;

import model.PaymentType;
import java.util.List;

public interface PaymentTypeDAO {
    List<PaymentType> getAllPaymentTypes();
}