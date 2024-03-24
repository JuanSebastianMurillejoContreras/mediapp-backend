package com.mitocode.service;

import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.service.impl.ICRUD;

import java.util.List;

public interface IConsultService extends ICRUD<Consult, Integer> {
    Consult saveTransactional(Consult consult, List<Exam> exams);
}
