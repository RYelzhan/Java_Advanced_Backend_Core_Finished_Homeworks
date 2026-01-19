import com.epam.jmp.impl.bank.BankImpl;

module jmp.cloud.bank.impl {
    requires transitive jmp.bank.api;
    requires jmp.dto;

    provides com.epam.jmp.service.bank.Bank with BankImpl;

    exports com.epam.jmp.impl.bank;
}
