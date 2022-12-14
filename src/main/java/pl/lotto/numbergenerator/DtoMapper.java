package pl.lotto.numbergenerator;

import pl.lotto.numbergenerator.dto.WinningNumbersDto;

import java.util.Optional;

class DtoMapper {
    static WinningNumbersDto mapWinningNumbersToDto(WinningNumbers winningNumbers) {
        return new WinningNumbersDto(Optional.ofNullable(winningNumbers.getWinningNumbers()), winningNumbers.getDrawDate());
    }
}
