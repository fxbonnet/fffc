package com.octo.fffc.service;

import com.octo.fffc.model.InputRequestDto;

/**
 * @author alanterriaga
 * @project FFFC
 */
public interface FileFormatService {

    boolean formatAndCreateFile(InputRequestDto inputRequestDto) throws Exception;
}
