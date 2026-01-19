module jmp.app.test {
    requires jmp.dto;
    requires jmp.bank.api;
    requires jmp.cloud.bank.impl;
    requires jmp.service.api;
    requires jmp.cloud.service.impl;
    requires org.junit.jupiter.api;

    uses com.epam.jmp.service.bank.Bank;
    uses com.epam.jmp.service.Service;

    opens com.epam.jmp.app to org.junit.platform.commons;
}