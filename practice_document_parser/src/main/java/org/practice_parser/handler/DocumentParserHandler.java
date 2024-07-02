package org.practice_parser.handler;

import org.practice_parser.dto.DocumentDto;

public interface DocumentParserHandler {
    DocumentDto parseDocument(String filePath);
}