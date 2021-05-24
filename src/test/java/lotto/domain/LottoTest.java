package lotto.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static lotto.domain.LottoFixture.번호_0개_일치;
import static lotto.domain.LottoFixture.번호_1개_일치;
import static lotto.domain.LottoFixture.번호_2개_일치;
import static lotto.domain.LottoFixture.번호_3개_일치;
import static lotto.domain.LottoFixture.번호_4개_일치;
import static lotto.domain.LottoFixture.번호_5개_일치;
import static lotto.domain.LottoFixture.번호_6개_일치;
import static lotto.domain.LottoFixture.우승번호;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class LottoTest {

    @Test
    void 로또_한_장의_가격은_1000원이다() {
        Lotto lotto = new Lotto(LottoNumberGenerator.get());
        assertThat(lotto.PRICE).isEqualTo(1000);
    }

    @Test
    void 로또는_1부터_45까지의_숫자_중에서_발행한다() {
        Lotto lotto = new Lotto(LottoNumberGenerator.get());
        assertThat(lotto.numbers).allMatch(num -> num <= 45).allMatch(num -> num >= 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 46})
    void 로또는_1부터_45사이가_아닌_값을_입력할_수_없다(int invalidNumber) {
        List<Integer> 잘못된_값 = new ArrayList<>(번호_0개_일치.numbers);
        잘못된_값.add(0, invalidNumber);
        assertThatIllegalArgumentException().isThrownBy(() -> new Lotto(잘못된_값));
    }

    @Test
    void 로또는_중복된_값을_입력할_수_없다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Lotto(Lists.newArrayList(1, 1, 1, 1, 1, 1)));
    }

    @Test
    void 로또는_6자리_수_이다() {
        Lotto lotto = new Lotto(LottoNumberGenerator.get());
        assertThat(lotto.numbers).hasSize(6);
    }

    @ParameterizedTest
    @MethodSource("provider_로또는_입력받은_로또와_일치하는_번호의_수를_알려준다")
    void 로또는_입력받은_로또와_일치하는_번호의_수를_알려준다(Lotto lotto, int expected) {
        assertThat(lotto.matchCountWith(우승번호)).isEqualTo(expected);
    }

    static Stream<Arguments> provider_로또는_입력받은_로또와_일치하는_번호의_수를_알려준다() {
        return Stream.of(Arguments.of(번호_6개_일치, 6), Arguments.of(번호_5개_일치, 5), Arguments.of(번호_4개_일치, 4),
                         Arguments.of(번호_3개_일치, 3), Arguments.of(번호_2개_일치, 2), Arguments.of(번호_1개_일치, 1),
                         Arguments.of(번호_0개_일치, 0));
    }
}
