module jmp.app {
    requires transitive jmp.bank.api;
    requires transitive jmp.service.api;
    requires jmp.dto;

    uses com.epam.jmp.service.Bank;
    uses com.epam.jmp.service.Service;

    exports com.epam.jmp.app;
}