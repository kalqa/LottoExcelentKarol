package pl.lotto.resultannouncer;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lotto.resultannouncer.dto.LotteryAnnouncementDto;
import pl.lotto.resultchecker.ResultCheckerFacade;
import pl.lotto.resultchecker.dto.LotteryResultDto;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ResultAnnouncerFacadeTest {

    static ResultCheckerFacade resultCheckerFacade;
    static ResultAnnouncerFacade resultAnnouncerFacade;

    @BeforeAll
    static void init() {
        resultCheckerFacade = mock(ResultCheckerFacade.class);
        resultAnnouncerFacade = new ResultAnnouncerFacadeConfiguration().createFacadeForTest(resultCheckerFacade);
    }


    @Test
    void should_return_correct_lottery_results_with_winning_message_when_user_won() {
        //given
        LotteryResultDto lotteryResultDto = new LotteryResultDto(Optional.of(List.of(1, 2, 3, 4, 5, 6)),
                Optional.of(List.of(1, 2, 3, 4, 5, 6)), OptionalInt.of(6), "you won!");
        given(resultCheckerFacade.checkWinner(any(UUID.class)))
                .willReturn(lotteryResultDto);

        //when
        LotteryAnnouncementDto result = resultAnnouncerFacade.checkWinner(UUID.randomUUID());

        //then
        assertAll(() -> assertThat(result.yourNumbers().get()).isEqualTo(List.of(1, 2, 3, 4, 5, 6)),
                () -> assertThat(result.winningNumbers().get()).isEqualTo(List.of(1, 2, 3, 4, 5, 6)),
                () -> assertThat(result.hitNumbers().getAsInt()).isEqualTo(6),
                () -> assertThat(result.message()).isEqualTo("you won!"));
    }

    @Test
    void should_return_correct_lottery_results_with_losing_message_when_user_lost() {
        //given
        LotteryResultDto lotteryResultDto = new LotteryResultDto(Optional.of(List.of(1, 2, 8, 9, 10, 11)), Optional.of(List.of(1, 2, 3, 4, 5, 6)), OptionalInt.of(2), "you lost!");
        given(resultCheckerFacade.checkWinner(any(UUID.class))).willReturn(lotteryResultDto);

        //when
        LotteryAnnouncementDto result = resultAnnouncerFacade.checkWinner(UUID.randomUUID());

        //then
        assertAll(() -> assertThat(result.yourNumbers().get()).isEqualTo(List.of(1, 2, 8, 9, 10, 11)),
                () -> assertThat(result.winningNumbers().get()).isEqualTo(List.of(1, 2, 3, 4, 5, 6)),
                () -> assertThat(result.hitNumbers().getAsInt()).isEqualTo(2),
                () -> assertThat(result.message()).isEqualTo("you lost!"));
    }

    @Test
    void should_return_result_with_empty_credentials_and_invalid_message_when_user_gave_invalid_id() {
        //LotteryResult(Optional.empty(), Optional.empty(), OptionalInt.empty(), "invalid id");
        //given
        LotteryResultDto lotteryResultDto = new LotteryResultDto(Optional.empty(), Optional.empty(), OptionalInt.empty(), "invalid");
        given(resultCheckerFacade.checkWinner(any(UUID.class))).willReturn(lotteryResultDto);

        //when
        LotteryAnnouncementDto result = resultAnnouncerFacade.checkWinner(UUID.randomUUID());

        //then
        assertAll(() -> assertThat(result.yourNumbers().isPresent()).isEqualTo(false),
                () -> assertThat(result.winningNumbers().isPresent()).isEqualTo(false),
                () -> assertThat(result.hitNumbers().isPresent()).isEqualTo(false),
                () -> assertThat(result.message()).isEqualTo("invalid"));
    }
}




















