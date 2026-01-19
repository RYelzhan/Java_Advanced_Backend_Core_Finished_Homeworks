module jmp.cloud.bank.impl {
    requires transitive jmp.bank.api;
    requires jmp.dto;

    provides com.epam.jmp.service.Bank with com.epam.jmp.impl.BankImpl;

    exports com.epam.jmp.impl;
}
