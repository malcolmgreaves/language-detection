package com.nitro.langdt

/** Trait representing Languages by their code. */
sealed trait LangCode extends Serializable {

  /** The 2-letter language code. */
  val code: String

  /** The complete language name, in English. */
  val fullName: String

  /** String representation of LangCode: the 2-letter code. */
  override def toString = code
}

object LangCode extends Serializable {

  /** The LangCode from it's 2-letter code. */
  def apply(s: String): Option[LangCode] =
    str2code.get(s.toLowerCase)

  /** Every implementation of LangCode. Aka. all language codes. */
  val allCodes: Seq[LangCode] = Seq(
    Af,
    Bg,
    Bn,
    Cs,
    Da,
    De,
    El,
    En,
    Es,
    Et,
    Fa,
    Fi,
    Fr,
    Gu,
    He,
    Hi,
    Hr,
    Hu,
    Id,
    It,
    Ja,
    Kn,
    Ko,
    Lt,
    Lv,
    Mk,
    Ml,
    Mr,
    Ne,
    Nl,
    No,
    Pa,
    Pl,
    Pt,
    Ro,
    Ru,
    Sk,
    Sl,
    So,
    Sq,
    Sv,
    Sw,
    Ta,
    Te,
    Th,
    Tl,
    Tr,
    Uk,
    Ur,
    Vi,
    Zhcn,
    Zhtw
  )

  /* Mapping of code (string) to language code (LangCode) for every langauge. */
  val str2code: Map[String, LangCode] =
    allCodes
      .map(lc => (lc.code, lc))
      .toMap

}

case object Af extends LangCode {
  override val code = "af"
  override val fullName = "Afrikaans"
}

case object Ar extends LangCode {
  override val code = "ar"
  override val fullName = "Arabic"
}

case object Bg extends LangCode {
  override val code = "bg"
  override val fullName = "Bulgarian"
}

case object Bn extends LangCode {
  override val code = "bn"
  override val fullName = "Bengali"
}

case object Cs extends LangCode {
  override val code = "cs"
  override val fullName = "Czech"
}

case object Da extends LangCode {
  override val code = "da"
  override val fullName = "Danish"
}

case object De extends LangCode {
  override val code = "de"
  override val fullName = "German"
}

case object El extends LangCode {
  override val code = "el"
  override val fullName = "Greek"
}

case object En extends LangCode {
  override val code = "en"
  override val fullName = "English"
}

case object Es extends LangCode {
  override val code = "es"
  override val fullName = "Spanish"
}

case object Et extends LangCode {
  override val code = "et"
  override val fullName = "Estonian"
}

case object Fa extends LangCode {
  override val code = "fa"
  override val fullName = "Persian"
}

case object Fi extends LangCode {
  override val code = "fi"
  override val fullName = "Finnish"
}

case object Fr extends LangCode {
  override val code = "fr"
  override val fullName = "French"
}

case object Gu extends LangCode {
  override val code = "gu"
  override val fullName = "Gujarati"
}

case object He extends LangCode {
  override val code = "he"
  override val fullName = "Hebrew"
}

case object Hi extends LangCode {
  override val code = "hi"
  override val fullName = "Hindi"
}

case object Hr extends LangCode {
  override val code = "hr"
  override val fullName = "Croatian"
}

case object Hu extends LangCode {
  override val code = "hu"
  override val fullName = "Hungarian"
}

case object Id extends LangCode {
  override val code = "id"
  override val fullName = "Indonesian"
}

case object It extends LangCode {
  override val code = "it"
  override val fullName = "Italian"
}

case object Ja extends LangCode {
  override val code = "ja"
  override val fullName = "Japanese"
}

case object Kn extends LangCode {
  override val code = "kn"
  override val fullName = "Kannada"
}

case object Ko extends LangCode {
  override val code = "ko"
  override val fullName = "Korean"
}

case object Lt extends LangCode {
  override val code = "lt"
  override val fullName = "Lithuanian"
}

case object Lv extends LangCode {
  override val code = "lv"
  override val fullName = "Latvian"
}

case object Mk extends LangCode {
  override val code = "mk"
  override val fullName = "Macedonian"
}

case object Ml extends LangCode {
  override val code = "ml"
  override val fullName = "Malayalam"
}

case object Mr extends LangCode {
  override val code = "mr"
  override val fullName = "Marathi"
}

case object Ne extends LangCode {
  override val code = "ne"
  override val fullName = "Nepali"
}

case object Nl extends LangCode {
  override val code = "nl"
  override val fullName = "Dutch"
}

case object No extends LangCode {
  override val code = "no"
  override val fullName = "Norwegian"
}

case object Pa extends LangCode {
  override val code = "pa"
  override val fullName = "Punjabi"
}

case object Pl extends LangCode {
  override val code = "pl"
  override val fullName = "Polish"
}

case object Pt extends LangCode {
  override val code = "pt"
  override val fullName = "Portuguese"
}

case object Ro extends LangCode {
  override val code = "ro"
  override val fullName = "Romanian"
}

case object Ru extends LangCode {
  override val code = "ru"
  override val fullName = "Russian"
}

case object Sk extends LangCode {
  override val code = "sk"
  override val fullName = "Slovak"
}

case object Sl extends LangCode {
  override val code = "sl"
  override val fullName = "Slovenian"
}

case object So extends LangCode {
  override val code = "so"
  override val fullName = "Somali"
}

case object Sq extends LangCode {
  override val code = "sq"
  override val fullName = "Albanian"
}

case object Sv extends LangCode {
  override val code = "sv"
  override val fullName = "Swedish"
}

case object Sw extends LangCode {
  override val code = "sw"
  override val fullName = "Swahili"
}

case object Ta extends LangCode {
  override val code = "ta"
  override val fullName = "Tamil"
}

case object Te extends LangCode {
  override val code = "te"
  override val fullName = "Telugu"
}

case object Th extends LangCode {
  override val code = "th"
  override val fullName = "Thai"
}

case object Tl extends LangCode {
  override val code = "tl"
  override val fullName = "Tagalog"
}

case object Tr extends LangCode {
  override val code = "tr"
  override val fullName = "Turkish"
}

case object Uk extends LangCode {
  override val code = "uk"
  override val fullName = "Ukrainian"
}

case object Ur extends LangCode {
  override val code = "ur"
  override val fullName = "Urdu"
}

case object Vi extends LangCode {
  override val code = "vi"
  override val fullName = "Vietnamese"
}

case object Zhcn extends LangCode {
  override val code = "zh-cn"
  override val fullName = "Chinese, Simplified"
}

case object Zhtw extends LangCode {
  override val code = "zh-tw"
  override val fullName = "Chinese, Traditional"
}