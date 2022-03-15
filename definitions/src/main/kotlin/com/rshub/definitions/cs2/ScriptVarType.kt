package com.rshub.definitions.cs2

import com.rshub.utilities.TextUtils.charToCp1252

enum class ScriptVarType(val id: Int, val display: String, val c: Char, val type: BaseVarType, val defaultVal: Any) {
    INT(0, "INT", 'i', BaseVarType.INTEGER, 0), BOOLEAN(1, "BOOLEAN", '1', BaseVarType.INTEGER, 0), TYPE_2(
        2,
        "TYPE_2",
        '2',
        BaseVarType.INTEGER,
        -1
    ),
    QUEST(3, "QUEST", ':', BaseVarType.INTEGER, -1), QUESTHELP(4, "QUESTHELP", ',', BaseVarType.INTEGER, -1), CURSOR(
        5,
        "CURSOR",
        '@',
        BaseVarType.INTEGER,
        -1
    ),
    SEQ(6, "SEQ", 'A', BaseVarType.INTEGER, -1), COLOUR(7, "COLOUR", 'C', BaseVarType.INTEGER, -1), LOC_SHAPE(
        8,
        "LOC_SHAPE",
        'H',
        BaseVarType.INTEGER,
        -1
    ),
    COMPONENT(9, "COMPONENT", 'I', BaseVarType.INTEGER, -1), IDKIT(10, "IDKIT", 'K', BaseVarType.INTEGER, -1), MIDI(
        11,
        "MIDI",
        'M',
        BaseVarType.INTEGER,
        -1
    ),
    NPC_MODE(12, "NPC_MODE", 'N', BaseVarType.INTEGER, -1), TYPE_13(
        13,
        "TYPE_13",
        'O',
        BaseVarType.INTEGER,
        -1
    ),  // TODO
    SYNTH(14, "SYNTH", 'P', BaseVarType.INTEGER, -1), TYPE_15(15, "TYPE_15", 'Q', BaseVarType.INTEGER, -1),  // TODO
    AREA(16, "AREA", 'R', BaseVarType.INTEGER, -1), STAT(17, "STAT", 'S', BaseVarType.INTEGER, -1), NPC_STAT(
        18,
        "NPC_STAT",
        'T',
        BaseVarType.INTEGER,
        -1
    ),
    WRITEINV(19, "WRITEINV", 'V', BaseVarType.INTEGER, -1), MESH(20, "MESH", '^', BaseVarType.INTEGER, -1), MAPAREA(
        21,
        "MAPAREA",
        '`',
        BaseVarType.INTEGER,
        -1
    ),
    WORLDTILE(22, "WORLDTILE", 'c', BaseVarType.INTEGER, -1), GRAPHIC(
        23,
        "GRAPHIC",
        'd',
        BaseVarType.INTEGER,
        -1
    ),
    CHATPHRASE(24, "CHATPHRASE", 'e', BaseVarType.INTEGER, -1), FONTMETRICS(
        25,
        "FONTMETRICS",
        'f',
        BaseVarType.INTEGER,
        -1
    ),
    ENUM(26, "ENUM", 'g', BaseVarType.INTEGER, -1), TYPE_27(27, "TYPE_27", 'h', BaseVarType.INTEGER, -1),  // TODO
    JINGLE(28, "JINGLE", 'j', BaseVarType.INTEGER, -1), CHATCAT(29, "CHATCAT", 'k', BaseVarType.INTEGER, -1), OBJECT(
        30,
        "OBJECT",
        'l',
        BaseVarType.INTEGER,
        -1
    ),
    MODEL(31, "MODEL", 'm', BaseVarType.INTEGER, -1), NPC(32, "NPC", 'n', BaseVarType.INTEGER, -1), ITEM(
        33,
        "ITEM",
        'o',
        BaseVarType.INTEGER,
        -1
    ),
    PLAYER_UID(34, "PLAYER_UID", 'p', BaseVarType.INTEGER, -1), TYPE_35(
        35,
        "TYPE_35",
        'r',
        BaseVarType.LONG,
        -1L
    ),  // TODO
    STRING(36, "STRING", 's', BaseVarType.STRING, ""), SPOTANIM(37, "SPOTANIM", 't', BaseVarType.INTEGER, -1), NPC_UID(
        38,
        "NPC_UID",
        'u',
        BaseVarType.INTEGER,
        -1
    ),
    INV(39, "INV", 'v', BaseVarType.INTEGER, -1), TEXTURE(40, "TEXTURE", 'x', BaseVarType.INTEGER, -1), CATEGORY(
        41,
        "CATEGORY",
        'y',
        BaseVarType.INTEGER,
        -1
    ),
    CHAR(42, "CHAR", 'z', BaseVarType.INTEGER, -1), LASER(43, "LASER", '|', BaseVarType.INTEGER, -1), BAS(
        44,
        "BAS",
        '\u20ac',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_45(45, "TYPE_45", '\u0192', BaseVarType.INTEGER, -1),  // TODO
    COLLISION_GEOMETRY(46, "COL_GEO", '\u2021', BaseVarType.INTEGER, -1), PHYSICS_MODEL(
        47,
        "PHYS_MODEL",
        '\u2030',
        BaseVarType.INTEGER,
        -1
    ),
    PHYSICS_CONTROL_MODIFIER(48, "PHYS_CTRL_MOD", '\u0160', BaseVarType.INTEGER, -1), CLANHASH(
        49,
        "CLANHASH",
        '\u0152',
        BaseVarType.LONG,
        -1L
    ),
    COORDFINE(50, "COORDFINE", '\u017d', BaseVarType.COORDFINE, "<TODO>"), CUTSCENE(
        51,
        "CUTSCENE",
        '\u0161',
        BaseVarType.INTEGER,
        -1
    ),
    ITEMCODE(53, "ITEMCODE", '\u00a1', BaseVarType.INTEGER, -1), TYPE_54(
        54,
        "TYPE_54",
        '\u00a2',
        BaseVarType.INTEGER,
        -1
    ),  //TODO
    MAPSCENEICON(55, "MAPSCENEICON", '\u00a3', BaseVarType.INTEGER, -1), CLANFORUMQFC(
        56,
        "CLANFORUMQFC",
        '\u00a7',
        BaseVarType.LONG,
        -1L
    ),
    VORBIS(57, "VORBIS", '\u00ab', BaseVarType.INTEGER, -1), VERIFY_OBJECT(
        58,
        "VERIFY_OBJECT",
        '\u00ae',
        BaseVarType.INTEGER,
        -1
    ),
    MAPELEMENT(59, "MAPELEMENT", '\u00b5', BaseVarType.INTEGER, -1), CATEGORYTYPE(
        60,
        "CATEGORYTYPE",
        '\u00b6',
        BaseVarType.INTEGER,
        -1
    ),
    SOCIAL_NETWORK(61, "SOCIAL_NETWORK", '\u00c6', BaseVarType.INTEGER, -1), HITMARK(
        62,
        "HITMARK",
        '\u00d7',
        BaseVarType.INTEGER,
        -1
    ),
    PACKAGE(63, "PACKAGE", '\u00de', BaseVarType.INTEGER, -1), PARTICLE_EFFECTOR(
        64,
        "PARTICLE_EFFECTOR",
        '\u00e1',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_65(65, "TYPE_65", '\u00e6', BaseVarType.INTEGER, -1),  // TODO
    PARTICLE_EMITTER(66, "PARTICLE_EMITTER", '\u00e9', BaseVarType.INTEGER, -1), PLOGTYPE(
        67,
        "PLOGTYPE",
        '\u00ed',
        BaseVarType.INTEGER,
        -1
    ),
    UNSIGNED_INT(68, "UINT", '\u00ee', BaseVarType.INTEGER, -1), SKYBOX(
        69,
        "SKYBOX",
        '\u00f3',
        BaseVarType.INTEGER,
        -1
    ),
    SKYDECOR(70, "SKYDECOR", '\u00fa', BaseVarType.INTEGER, -1), HASH64(
        71,
        "HASH64",
        '\u00fb',
        BaseVarType.LONG,
        -1L
    ),
    INPUTTYPE(72, "INPUTTYPE", '\u00ce', BaseVarType.INTEGER, -1), STRUCT(
        73,
        "STRUCT",
        'J',
        BaseVarType.INTEGER,
        -1
    ),
    DBROW(74, "DBROW", '\u00d0', BaseVarType.INTEGER, -1), TYPE_75(
        75,
        "TYPE_75",
        '\u00a4',
        BaseVarType.INTEGER,
        -1
    ),  // TODO
    TYPE_76(76, "TYPE_76", '\u00a5', BaseVarType.INTEGER, -1),  // TODO
    TYPE_77(77, "TYPE_77", '\u00e8', BaseVarType.INTEGER, -1),  // TODO
    TYPE_78(78, "TYPE_78", '\u00b9', BaseVarType.INTEGER, -1),  // TODO
    TYPE_79(79, "TYPE_79", '\u00b0', BaseVarType.INTEGER, -1),  // TODO
    TYPE_80(80, "TYPE_80", '\u00ec', BaseVarType.INTEGER, -1),  // TODO
    TYPE_81(81, "TYPE_81", '\u00eb', BaseVarType.INTEGER, -1),  // TODO
    TYPE_83(83, "TYPE_82", '\u00fe', BaseVarType.INTEGER, -1),  // TODO
    TYPE_84(84, "TYPE_83", '\u00fd', BaseVarType.INTEGER, -1),  // TODO
    TYPE_85(85, "TYPE_84", '\u00ff', BaseVarType.INTEGER, -1),  // TODO
    TYPE_86(86, "TYPE_85", '\u00f5', BaseVarType.INTEGER, -1),  // TODO
    TYPE_87(87, "TYPE_86", '\u00f4', BaseVarType.INTEGER, -1),  // TODO
    TYPE_88(88, "TYPE_87", '\u00f6', BaseVarType.INTEGER, -1),  // TODO
    GWC_PLATFORM(89, "GWC_PLATFORM", '\u00f2', BaseVarType.INTEGER, -1), TYPE_90(
        90,
        "TYPE_90",
        '\u00dc',
        BaseVarType.INTEGER,
        -1
    ),  // TODO
    TYPE_91(91, "TYPE_91", '\u00f9', BaseVarType.INTEGER, -1),  // TODO
    TYPE_92(92, "TYPE_92", '\u00ef', BaseVarType.INTEGER, -1),  // TODO
    TYPE_93(93, "TYPE_93", '\u00af', BaseVarType.INTEGER, -1),  // TODO
    BUG_TEMPLATE(94, "BUG_TEMPLATE", '\u00ea', BaseVarType.INTEGER, -1), BILLING_AUTH_FLAG(
        95,
        "BILLING_AUTH_FLAG",
        '\u00f0',
        BaseVarType.INTEGER,
        -1
    ),
    ACCOUNT_FEATURE_FLAG(96, "ACCOUNT_FEATURE_FLAG", '\u00e5', BaseVarType.INTEGER, -1), INTERFACE(
        97,
        "INTERFACE",
        'a',
        BaseVarType.INTEGER,
        -1
    ),
    TOPLEVELINTERFACE(98, "TOPLEVELITNERFACE", 'F', BaseVarType.INTEGER, -1), OVERLAYINTERFACE(
        99,
        "OVERLAYINTERFACE",
        'L',
        BaseVarType.INTEGER,
        -1
    ),
    CLIENTINTERFACE(100, "CLIENTINTERFACE", '\u00a9', BaseVarType.INTEGER, -1), MOVESPEED(
        101,
        "MOVESPEED",
        '\u00dd',
        BaseVarType.INTEGER,
        -1
    ),
    MATERIAL(102, "MATERIAL", '\u00ac', BaseVarType.INTEGER, -1), SEQGROUP(
        103,
        "SEQGROUP",
        '\u00f8',
        BaseVarType.INTEGER,
        -1
    ),
    TEMP_HISCORE(104, "TEMP_HS", '\u00e4', BaseVarType.INTEGER, -1), TEMP_HISCORE_LENGTH_TYPE(
        105,
        "TEMP_HS_LEN",
        '\u00e3',
        BaseVarType.INTEGER,
        -1
    ),
    TEMP_HISCORE_DISPLAY_TYPE(
        106,
        "TEMP_HS_DISPLAY",
        '\u00e2',
        BaseVarType.INTEGER,
        -1
    ),
    TEMP_HISCORE_CONTRIBUTE_RESULT(107, "TEMPH_HS_CONTRIB_RESULT", '\u00e0', BaseVarType.INTEGER, -1), AUDIOGROUP(
        108,
        "AUDIOGROUP",
        '\u00c0',
        BaseVarType.INTEGER,
        -1
    ),
    AUDIOMIXBUSS(109, "AUDIOMIXBUS", '\u00d2', BaseVarType.INTEGER, -1), LONG(
        110,
        "LONG",
        '\u00cf',
        BaseVarType.LONG,
        0L
    ),
    CRM_CHANNEL(111, "CRM_CHANNEL", '\u00cc', BaseVarType.INTEGER, -1), HTTP_IMAGE(
        112,
        "HTTP_IMAGE",
        '\u00c9',
        BaseVarType.INTEGER,
        -1
    ),
    POP_UP_DISPLAY_BEHAVIOUR(113, "POP_UP_DISPLAY_BEHAVIOUR", '\u00ca', BaseVarType.INTEGER, -1), POLL(
        114,
        "POLL",
        '\u00f7',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_115(115, "TYPE_115", '\u00bc', BaseVarType.LONG, -1L),  // TODO
    TYPE_116(116, "TYPE_116", '\u00bd', BaseVarType.LONG, -1L),  // TODO
    POINTLIGHT(117, "POINTLIGHT", '\u2022', BaseVarType.INTEGER, -1), PLAYER_GROUP(
        118,
        "PLAYER_GROUP",
        '\u00c2',
        BaseVarType.LONG,
        -1L
    ),
    PLAYER_GROUP_STATUS(119, "PLAYER_GROUP_STATUS", '\u00c3', BaseVarType.INTEGER, -1), PLAYER_GROUP_INVITE_RESULT(
        120,
        "PLAYER_GROUP_INVITE_RESULT",
        '\u00c5',
        BaseVarType.INTEGER,
        -1
    ),
    PLAYER_GROUP_MODIFY_RESULT(
        121,
        "PLAYER_GROUP_MODIFY_RESULT",
        '\u00cb',
        BaseVarType.INTEGER,
        -1
    ),
    PLAYER_GROUP_JOIN_OR_CREATE_RESULT(
        122,
        "PLAYER_GROU_JOIN_OR_CREATE_RESULT",
        '\u00cd',
        BaseVarType.INTEGER,
        -1
    ),
    PLAYER_GROUP_AFFINITY_MODIFY_RESULT(
        123,
        "PLAYER_GROU_AFFINITY_MODIFY_RESULT",
        '\u00d5',
        BaseVarType.INTEGER,
        -1
    ),
    PLAYER_GROUP_DELTA_TYPE(124, "PLAYER_GROUP_DELTA_TYPE", '\u00b2', BaseVarType.INTEGER, -1), CLIENT_TYPE(
        125,
        "CLIENT_TYPE",
        '\u00aa',
        BaseVarType.INTEGER,
        -1
    ),
    TELEMETRY_INTERVAL(126, "TELEMETRY_INTERVAL", '\u0000', BaseVarType.INTEGER, 0), WORLDAREA(
        127,
        "WORLDAREA",
        '\u0000',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_128(128, "TYPE_128", '\u0000', BaseVarType.INTEGER, -1), TYPE_129(
        129,
        "TYPE_129",
        '\u00d8',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_130(130, "TYPE_130", '\u0000', BaseVarType.INTEGER, -1), TYPE_131(
        131,
        "TYPE_131",
        '\u02dc',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_200(200, "TYPE_200", 'X', BaseVarType.INTEGER, -1), TYPE_201(
        201,
        "TYPE_201",
        'W',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_202(202, "TYPE_202", 'b', BaseVarType.INTEGER, -1), TYPE_203(
        203,
        "TYPE_203",
        'B',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_204(204, "TYPE_204", '4', BaseVarType.INTEGER, -1), TYPE_205(
        205,
        "TYPE_205",
        'w',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_206(206, "TYPE_206", 'q', BaseVarType.INTEGER, -1), TYPE_207(
        207,
        "TYPE_207",
        '0',
        BaseVarType.INTEGER,
        -1
    ),
    TYPE_208(208, "TYPE_208", '6', BaseVarType.INTEGER, -1);

    fun rawToReadable(id: Any): Any {
        if (id is String && type == BaseVarType.STRING) return id else require(id !is String) { "Raw type is string, but base type is $type" }
        return when (this) {
            COMPONENT -> {
                val hash = id as Int
                "[" + (hash shr 16) + ", " + (hash and 0xffff) + "]"
            }
            else -> id
        }
    }

    companion object {
        private val legacyMappings = arrayOfNulls<ScriptVarType>(256)
        private val BY_ID: MutableMap<Int, ScriptVarType> = HashMap()
        private val BY_CHAR: MutableMap<Char, ScriptVarType> = HashMap()

        init {
            for (t in values()) {
                legacyMappings[charToCp1252(t.c).toInt() and 0xff] = t
                BY_ID[t.id] = t
                BY_CHAR[t.c] = t
            }
        }

        fun getById(id: Int): ScriptVarType? {
            return BY_ID[id]
        }

        fun getByChar(c: Char): ScriptVarType? {
            return BY_CHAR[c]
        }
    }
}