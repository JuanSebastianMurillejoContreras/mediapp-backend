package com.mitocode.dto;

public record PatientRecord(
        int idPatient,
        String primaryName,
        String surName,
        String dni,
        String address,
        String phone,
        String email
) {
}
