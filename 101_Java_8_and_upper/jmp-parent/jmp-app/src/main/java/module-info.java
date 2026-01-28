module jmp.app {
    requires jmp.dto;
    requires jmp.bank.api;
    requires jmp.service.api;

    uses com.epam.jmp.service.bank.Bank;
    uses com.epam.jmp.service.Service;
}